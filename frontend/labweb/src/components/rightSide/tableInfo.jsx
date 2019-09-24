import React, { Component } from 'react';
import './index.less'

import {getLabId} from '@/api'
import { Breadcrumb } from 'antd';


class TableInfo extends Component {
    constructor(props) {
        super(props);
        let navId = null,navName="",titleinfo="",infoName="";
        if(props.location.state){
            ({navId,navName,titleinfo,infoName} = props.location.state)
        }
        this.state = {
            infoId:navId,
            navName,
            titleinfo,
            dataList:{},
            infoName
          }
    }
   componentDidMount(){
        this.getLab();
    }
        //获取表格数据
        getLab = async()=>{
            let pathnameList = this.props.location.pathname.split("/");
            let order = Number(pathnameList[pathnameList.length-2]);
            let type = "";
            if([1,2,3,4].includes(order)){
                type="lab"
            }else if([30,31,32,33].includes(order)){
                type="news"
            }else if([34,35,36].includes(order)){
                type="notices"
            }else if([10,11].includes(order)){
                type="scientificResearch"
            }else if([37,38,39].includes(order)){
                type="news"
            }
            let data = await getLabId(this.state.infoId,type);
            if(data.result){
                this.setState({
                    dataList : data.result.detail,
                    navName:data.result.detail.category
                })
            }
        }
    render() { 
        let { dataList, titleinfo, navName, infoName } = this.state;
        return ( 
            <div className="right-container">
                 <Breadcrumb separator=">">
                    <Breadcrumb.Item>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>{titleinfo}</Breadcrumb.Item>
                    <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                    <Breadcrumb.Item >{infoName}</Breadcrumb.Item>
                </Breadcrumb>
                <div className="title-name title-info-name">
                    {dataList.mentorName || dataList.title }
                </div>
                <p dangerouslySetInnerHTML={{__html: dataList.context}}></p>
            </div>
         );
    }
}
 
export default TableInfo;
