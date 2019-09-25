import React, { Component } from 'react'
import { Typography, Col, Row } from 'antd';
import { withRouter  } from 'react-router-dom';
import  {NavTitle,NewsTitle,NoticeTitle} from "@utils/titleConfig"
import './index.less'
import {getNewsDay} from '../../utils/dateUtils'

const { Paragraph } = Typography;
class MoreTitle extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type, //用于区分Home的more样式 现有1~5种
            titleinfo: props.titleinfo,
            dataList:  [],
            imgList:""
        }
    }
    jumpAll(){
        switch(this.state.titleinfo){
            case NewsTitle[2].en: 
            this.props.history.push("news/32");
            break;
            case NavTitle[2].en: 
            this.props.history.push("news/31");
            break;
            case NewsTitle[3].en: 
            this.props.history.push("news/33");
            break;
            case NavTitle[3].en: 
            this.props.history.push("notices/34");
            break;
            case NoticeTitle[2].en: 
            this.props.history.push("notices/36");
            break;
            case NavTitle[5].en: 
            this.props.history.push("resources/37");
            break;
            default:;
        }
        window.scroll({ top: 0 })
    }
    jump=(data)=>{
        let url = "";
        switch(this.state.titleinfo){
            case NavTitle[2].en: 
            url = `/news/${data.categoryId}/info`
            break;
            case NewsTitle[3].en: url = "/news/33/info"
            break;
            case NoticeTitle[2].en: url = `/notices/${data.categoryId}/info`
            break;
            case NavTitle[3].en: url = "/notices/36/info"
            break;
            case NewsTitle[2].en: url = "/news/32/info"
            break;
            case NavTitle[5].en: url = `/resources/${data.categoryId}/info`
            break;
            default:;
        }
        let childList = {
            navId:data.id,
            titleinfo:this.state.titleinfo
        }
            this.props.history.push(`${url}`,childList);
            window.scroll({ top: 0 })
        }
    /**
     * 获取Homemore数据 type 1，2，3的情况
     */
    nodeList = (data) =>
        data.map((item,index) => {
            return (
                <Row type="flex" className="more-container" key={index}>
                    <Col span={16} className="more-container-left">
                        <Paragraph ellipsis className="curp" onClick={this.jump.bind(this,item)}>{item.title}</Paragraph>
                    </Col>
                    <Col span={8} className="more-container-right">
                        {getNewsDay(item.publishDate)}
                    </Col>
                </Row>
            )
        })
    /**
     * 获取Homemore数据 type 4的情况
     */
    nodeList2 = (data) =>
        data.map((item, index) => {
            return (
                <Col span={6} className="more-container-left"  key={index}>
                    <div className="resources-conent" onClick={this.jump.bind(this,item)}>
                        <img src={item.imageurl} alt=""/>
                        <Paragraph ellipsis>{item.title}</Paragraph>
                    </div>
                </Col>
            )
        })
    render() {
        let { titleinfo, type, dataList=[] ,imgList="" } = this.state;
        if(this.props.datalist && this.props.datalist.length){
            dataList = this.props.datalist;
        }
        if(this.props.imglist){
            imgList = this.props.imglist;
        }
        return (
            <div className="more-info">
                <Row type="flex" className="more-header" style={{ marginTop: (["1"].includes(type)) ? "0" : "32px" }}>
                    <Col span={18} className="more-header-left">
                        <span>{titleinfo}</span>
                    </Col>
                    {
                        type !== "5" && <Col span={6} className="more-header-right">
                            <span className="curp"  onClick={this.jumpAll.bind(this)}>more...</span>
                        </Col>
                    }
                </Row>
                <div className={"3" === this.state.type ? "addBg" : null} >
                    {
                        ["1", "3"].includes(type) && this.nodeList(dataList)
                    }
                    {
                        ["2"].includes(type) && <Row type="flex" justify="space-between">
                            <Col span={11}>
                                <img className="news-img" src={imgList} width="280" alt="实验室" />
                            </Col>
                            <Col span={13} className="more-pic-info">
                                {
                                    this.nodeList(dataList)
                                }
                            </Col>
                        </Row>
                    }
                    {
                        type === "4" && <Row type="flex" justify="space-around" className="more-container">
                            {this.nodeList2(dataList)}
                        </Row>

                    }
                    {
                        type === "5" && <Row className="FriendLink">
                            {
                               dataList.map((item,index) => {
                                    return (
                                            <Col key={index} span={8}> <a href={JSON.parse(item.context).url} target="_blank" rel="noopener noreferrer">{JSON.parse(item.context).name}</a></Col>
                                    )
                                })
                            }

                        </Row>
                    }
                </div>
            </div>
        );
    }
}

export default withRouter(MoreTitle);