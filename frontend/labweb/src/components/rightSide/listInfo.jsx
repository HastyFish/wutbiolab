import React, { Component } from 'react';
import './index.less'

import {getLabId} from '@/api'
import {getNewsDay} from '../../utils/dateUtils'
import { Breadcrumb } from 'antd';
import TimeTag from '@/assets/images/time.png'
import {getTitleinfo} from '@utils/tools'

class ListInfo extends Component {
    constructor(props) {
        super(props);
        let navId = null,navName="",titleinfo="";
        if(props.location.state){
            ({navId,navName,titleinfo} = props.location.state)
        }
        this.state = {
            infoId:navId,
            navName,
            titleinfo,
            dataList:{},
            next:{},
            previous:{}
          }
    }
        //动态获取父级传来的值再做渲染
        componentWillReceiveProps(nextProps) {
            if (nextProps.location.state) {
                this.setState({
                    infoId:nextProps.location.state.navId,
                    titleinfo :nextProps.location.state.titleinfo,
                    navName :nextProps.location.state.navName
                },()=>{ this.getLab()})
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
                type="notice"
            }else if([10,11].includes(order)){
                type="scientificResearch"
            }else if([37,38,39].includes(order)){
                type="resource/published"
            }
            let data = await getLabId(this.state.infoId,type);
            if(data.result){
                this.setState({
                    dataList : data.result.detail,
                    next : data.result.next,
                    previous : data.result.previous,
                    navName :  data.result.detail.category
                })
            }
        }
        nextEvent = (data)=>{
            let pathnameList = this.props.location.pathname.split("/");
            let order = Number(pathnameList[pathnameList.length-2]);
            let url = "";

            if([1,2,3,4].includes(order)){
                url = "/introduction"
            }else if([30,31,32,33].includes(order)){
                url = "/news"
            }else if([34,35,36].includes(order)){
                url = "/notices"
            }else if([10,11].includes(order)){
                url = "/scientific"
            }else if([37,38,39].includes(order)){
                url = "/resources"
            }
            let childList = {
                navId:data.id,
                navName:this.state.navName,
                titleinfo:this.state.titleinfo
            }

            this.props.history.push(`${url}/${order}/info`,childList);
           
        }
    render() { 
        let {dataList={},next,previous,titleinfo,navName} = this.state;
        let pathnameList = this.props.location.pathname.split("/");
        let order = Number(pathnameList[pathnameList.length-2]);
        titleinfo = getTitleinfo(order);
        return ( 
            <div className="right-container">
                 <Breadcrumb separator=">">
                    <Breadcrumb.Item>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>{titleinfo}</Breadcrumb.Item>
                    <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                </Breadcrumb>
                <div className="title-name title-info-name">
                  {dataList.title}
                </div>
                <div className="title-contnet">
                    <p className="title-contnet-time">  <img src={TimeTag} alt="时间"/>{getNewsDay(dataList.publishDate)}</p>
                    <p  dangerouslySetInnerHTML={{__html: dataList.context}}></p>
                </div>
                <div className="title-next">
                    {previous && <p>上一篇 ：<span onClick={this.nextEvent.bind(this,previous)}>{previous.title}</span></p>}
                    {!previous && <p>上一篇 ：没有了</p>}
                    {next && <p>下一篇 ：<span onClick={this.nextEvent.bind(this,next)}>{next.title}</span></p>}
                    {!next && <p>下一篇 ：没有了</p>}
                </div>
                
            </div>
         );
    }
}
 
export default ListInfo;
