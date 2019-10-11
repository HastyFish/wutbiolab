import React from 'react';
import './index.less'
import { Breadcrumb,Pagination, Row,Col ,ConfigProvider,Typography } from 'antd';
import {getLabLabCategoryId} from '@/api'
import {getNewsDay} from '@utils/dateUtils'
import {getTitleinfo} from '@utils/tools'
import zhCN from 'antd/es/locale/zh_CN';
const { Paragraph } = Typography;
class ListPage extends React.PureComponent {
    constructor(props) {
        super(props);
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length-1]) ;
        this.state = { 
            titleName : "",
            dataList:{},
            navId,
            titleinfo:"",
            navName:"",
            pageNum:1,
            pageSize:10,
            total:0,
            locale: zhCN,
         }
    }
       //每页展示条数发生变化的回调函数
  onShowSizeChange = async (current, size) => {
    let {pageNum,pageSize} = this.state;
    pageNum = 1;
    pageSize = size;
    this.setState({
        pageNum,
        pageSize
    },()=>{ this.getTable()})
   
  }   
      //当前页码变化的回调函数
  changePage = async (page) => {
    let {pageNum} = this.state;
    pageNum = page;
    this.setState({
        pageNum
    },()=>{ this.getTable()})
  }
      //获取表格数据
      getTable = async()=>{
        if(this.props.location.state){
            this.setState({
                titleinfo :this.props.location.state.titleinfo,
            })
        }
          let {navId,pageNum,pageSize} = this.state;
          let type = "";
          if([1,2,3,4].includes(navId)){
                type="lab"
          }else if([30,31,32,33].includes(navId)){
            type="news"
          }else if([34,35,36].includes(navId)){
            type="notice"
          }else if([10,11].includes(navId)){
            type="scientificResearch"
          }else if([37,38,39].includes(navId)){
            type="resource/published"
          }
        let data = await getLabLabCategoryId(navId,pageNum,pageSize,type);
        if(data.result){
            this.setState({
                dataList : data.result.list,
                total:data.result.total,
                navName:data.result.category
            })
        }else{ 
            this.setState({
                dataList : [],
                total:0,
            })
        }
    }
    // //动态获取父级传来的值再做渲染
    componentWillReceiveProps(nextProps) {
        if (nextProps.location.state) {
            this.setState({
                navId:nextProps.location.state.navId,
                titleinfo :nextProps.location.state.titleinfo,
                pageNum:1
            },()=>{ this.getTable()})
        }
    }
    componentDidMount(){
            this.getTable();
    }
    jump = (data)=>{
        let childList = {
            navId:data.id,
            titleinfo:this.state.titleinfo
        }
        let url = "";
        let navId = this.state.navId;
        if([1,2,3,4].includes(navId)){
            url = "/introduction"
        }else if([30,31,32,33].includes(navId)){
            url = "/news"
        }else if([34,35,36].includes(navId)){
            url = "/notices"
        }else if([10,11].includes(navId)){
            url = "/scientific"
        }else if([37,38,39].includes(navId)){
            url = "/resources"
        }
        this.props.history.push(`${url}/${navId}/info/${data.id}`,childList);
    }
    tableChild = (data)=>data.map((item,index)=>{
        return(
            <li key={index}>
               <Row type="flex">
                   <Col span={1}>
                    <span  className={`color-block ${index===0 ? "first-color-block" : null} `}></span> 
                   </Col>
                   <Col span={16}>
                      <Paragraph  ellipsis style={{display:"inline-block",width:550}} onClick={this.jump.bind(this,item)} className="curp">{item.title}</Paragraph>
                   </Col>
                   <Col span={6}  style={{textAlign:"right"}}>
                       {getNewsDay(item.publishDate)}
                   </Col>   
               </Row>
            </li>
            
        )
    })



    render() { 
        let {dataList,titleinfo,navName,pageNum,pageSize,total,navId,locale} = this.state;
        const page = {
            current: pageNum,
            showSizeChanger:true,
            defaultCurrent:pageNum,
            pageSize:pageSize,
            total:total,
            onShowSizeChange:this.onShowSizeChange,
            onChange: this.changePage,
            showTotal:(total)=>{
            return `共${total}条`
            }
        }
        titleinfo = getTitleinfo(navId);
        return ( 
            <div className="right-container">
                <Breadcrumb separator=">">
                                <Breadcrumb.Item>Home</Breadcrumb.Item>
                                <Breadcrumb.Item>{titleinfo}</Breadcrumb.Item>
                                <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                </Breadcrumb>
                    <div>
                    <div className="title-name">
                        {/* {navName} */}
                    </div>
                    {
                     dataList.length>0 &&    <ul className="list-page">
                         {this.tableChild(dataList)}
                     </ul>
                    }
                  
                       <div style={{textAlign:"right",width:810}}>
                        <ConfigProvider locale={locale}>
                        {
                                !!total &&  <Pagination {...page}/>
                            }
                                </ConfigProvider>
                            
                       </div>
                       
                   
                </div>
            </div>
         );
    }
}
 
export default ListPage;