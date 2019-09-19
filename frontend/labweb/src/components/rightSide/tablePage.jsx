import React, { Component } from 'react';
import './index.less'
import { Breadcrumb } from 'antd';
import TableInfo from '@components/rightSide/tableInfo';

import {getLabResearchTeam} from '@/api'

class TablePage extends React.PureComponent {
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
            infoName:''
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
        this.getTable(navId);
        
    }
    componentDidMount(){
        this.getTable(this.state.navId);
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
    tableNodes=(data)=>data.map((item,index)=>{
        return(
            <dd  key={index} onClick={this.jump.bind(this,item)}>{item.mentorName}</dd>
        )
    })
    tableChild = (data)=>data.map((item,index)=>{
        debugger;
        return(
            <dl key={index}>
                <dt>{item.mentorCategoryName}</dt>
                {item.labDetails.length >0 && this.tableNodes(item.labDetails)}
            </dl>
            
        )
    })

    
    //获取表格数据
    getTable = async(id)=>{
        let data = await getLabResearchTeam(id);
        if(data.result && data.result.length){
            this.setState({
                dataList : data.result,
                navId:id
            })
        }
    }
    render() { 
        let {dataList,titleinfo,navName,isInfo,infoId,infoName} = this.state;
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
                     dataList.length>0 &&   this.tableChild(dataList)
                    }
                </div>
                }
                {
                    isInfo  &&  <TableInfo infoId={infoId}></TableInfo>
                
                }
            </div>
         );
    }
}
 
export default TablePage;