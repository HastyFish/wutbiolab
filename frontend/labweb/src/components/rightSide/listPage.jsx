import React, { Component } from 'react';
import './index.less'
import { Breadcrumb,Pagination  } from 'antd';
import ListInfo from '@components/rightSide/listInfo';
import {getLabLabCategoryId} from '@/api'

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
            isInfo:false,
            infoId:null,
            infoName:'',
            pageNum:1,
            pageSize:10,
            total:0
         }
    }
       //每页展示条数发生变化的回调函数
  onShowSizeChange = async (current, size) => {
      debugger;
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
          let {navId,pageNum,pageSize} = this.state;
        let data = await getLabLabCategoryId(navId,pageNum,pageSize);
        if(data.result && data.result.list.length){
            this.setState({
                dataList : data.result.list,
                navId,
                total:data.result.total
            })
        }
    }
    // //动态获取父级传来的值再做渲染
    componentWillReceiveProps(nextProps) {
        let navId =null;
        if(nextProps.location.state){
            navId = nextProps.location.state.navId ;
        }else{
            navId =  this.state.navId
        }
        this.getTable();
        
    }
    componentDidMount(){
        this.getTable();
    }
    // tableChild=(data)=>{
    //     data.map()
    //     const [table1,table2] = data;
    //     let tableEle = '';
    //     if(table1 && table1.labDetails.length){
    //         table = table1.labDetails.map((item,index)=>{
    //             return(
    //                 <dt>{item.mentorName}</dt>
    //             )
    //         })
    //     }
    //     debugger;
    // }
    jump = (data)=>{
        debugger;
        this.setState({
            isInfo:true,
            infoId:data.id,
            infoName:data.mentorName
        })
    }
    tableChild = (data)=>data.map((item,index)=>{
        debugger;
        return(
            <li key={index} onClick={this.jump.bind(this,item)}>
                {item.title}
            </li>
            
        )
    })



    render() { 
        let {dataList,titleinfo,navName,isInfo,infoId,infoName,pageNum,pageSize,total} = this.state;
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
        debugger;
        if(this.props.location.state){
            titleinfo = this.props.location.state.titleinfo;
            navName = this.props.location.state.navName;
        }
        return ( 
            <div className="right-container">
                <Breadcrumb separator=">">
                                <Breadcrumb.Item>首页</Breadcrumb.Item>
                                <Breadcrumb.Item>{titleinfo}</Breadcrumb.Item>
                                <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                                <Breadcrumb.Item >{infoName}</Breadcrumb.Item>
                </Breadcrumb>
                {
                    !isInfo && <div>
                    <div className="title-name">
                        研究团队
                    </div>
                    {
                     dataList.length>0 &&    <ul>
                         {this.tableChild(dataList)}
                     </ul>
                    }
                    <Pagination {...page}/>
                </div>
                }
                {
                    isInfo  &&  <ListInfo infoId={infoId}></ListInfo>
                
                }
            </div>
         );
    }
}
 
export default ListPage;