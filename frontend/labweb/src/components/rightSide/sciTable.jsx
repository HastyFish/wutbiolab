import React from 'react';
import './index.less'
import { Breadcrumb,Table ,ConfigProvider } from 'antd';
import {getLabLabCategoryId} from '@/api'
import {getTitleinfo} from '@utils/tools'
import zhCN from 'antd/es/locale/zh_CN';



class SciTable extends React.PureComponent {
    constructor(props) {
        super(props);
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length-1]) ;
        this.state = { 
            titleName : "",
            dataList:[],
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
        let data = await getLabLabCategoryId(navId,pageNum,pageSize,"scientificResearch");
        if(data.result){
            this.setState({
                dataList : data.result.list,
                navName:data.result.category,
                total:data.result.total
            })
        }else{ 
            this.setState({
                dataList : [],
            })
        }
    }
    // //动态获取父级传来的值再做渲染
    componentWillReceiveProps(nextProps) {
        if (nextProps.location.state) {
            this.setState({
                navId:nextProps.location.state.navId,
                titleinfo :nextProps.location.state.titleinfo,
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
        let navId = this.state.navId;
        this.props.history.push(`/scientific/${navId}/info/${data.id}`,childList);
    }




    render() { 
        let {dataList,titleinfo,navName,pageNum,pageSize,total,navId,locale} = this.state;
        let columns;
        if(this.props.location.pathname.includes('12')){
            columns = [
                {
                  title: '专利名称',
                  dataIndex: 'title',
                  render: (path, text, index) => {
                    return (
                       <span className="curp sci-title" key={index} onClick={this.jump.bind(this,text)}>{path}</span>
                    )
                }
                },
                {
                  title: '第一作者',
                  dataIndex: 'author',
                },
                {
                  title: '发表年度',
                  dataIndex: 'publishYear',
                }
              ];
        }else{
            columns = [
                {
                  title: '论文题目',
                  dataIndex: 'title',
                  render: (path, text, index) => {
                    return (
                       <span className="curp sci-title" key={index} onClick={this.jump.bind(this,text)}>{path}</span>
                    )
                }
                },
                {
                  title: '刊物名称',  
                  dataIndex: 'periodicalName',
                },
                {
                  title: '第一作者',
                  dataIndex: 'author',
                },
                {
                  title: '发表年度',
                  dataIndex: 'publishYear',
                }
              ];
        }
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
                    <ConfigProvider locale={locale}>
                    <Table columns={columns} 
                    className='sci-table'
                        dataSource={dataList} 
                        bordered
                        pagination={total >0 ? page : false}
                        rowKey="id"
                        />
                    </ConfigProvider>
                    
                </div>
            </div>
         );
    }
}
 
export default SciTable;