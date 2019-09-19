import React, { Component } from 'react';
import { Breadcrumb } from 'antd';
import './index.less'
import {getLabOneById} from '@/api'

class OnlyInfo extends React.PureComponent {
    constructor(props) {
        super(props);
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length-1]) ;
        this.state = { 
            titleName : "",
            dataList:{},
            navId,
            titleinfo:"",
            navName:""
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
            this.getOne(navId);
        
    }
    componentDidMount(){
        this.getOne(this.state.navId);
    }
    //获取一段文字
     getOne = async(navId)=>{
        let data = await getLabOneById(navId);
        if(data.result){
            this.setState({
                dataList : data.result,
                navId
            })
        }
    }
    render() { 
        let {dataList,titleinfo,navName} = this.state;
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
                </Breadcrumb>
                <div className="title-name">
                    {dataList.title}
                </div>
                <p dangerouslySetInnerHTML={{__html: dataList.context}}></p>
            </div>
         );
    }
}
 
export default OnlyInfo