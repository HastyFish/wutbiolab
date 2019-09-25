import React, { Component } from 'react';
import './news.less';
import { Row, Col } from 'antd';
import { Switch, Route } from 'react-router-dom';
import ListInfo from '@components/rightSide/listInfo';
import ListPage from '@components/rightSide/listPage';
import  {NavTitle} from "@utils/titleConfig"


import {getNewAll} from '@/api'
import { Number } from 'core-js';


class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            titleinfo : NavTitle[2].en,
            navList : [],
            dataList:[],
            navId:30,
        }
    }
    /**
     * 切换导航
     */
    changeNav=(item,index)=>{
        this.setState({
            navId:item.id,
        })
        let childList = {
            navId:item.id,
            titleinfo:this.state.titleinfo
        }
        this.props.history.push(`/news/${item.id}`,childList);
    }
    componentWillMount(){
        let pathnameList = this.props.location.pathname.split("/");
        let navId = 0 ;
        if(pathnameList[pathnameList.length-1] === "info"){
            navId = Number(pathnameList[pathnameList.length-2])
        }else{
            navId = Number(pathnameList[pathnameList.length-1])
        }
        this.setState({
            navId
        })
    }
    //初始页面数据 默认第一条数据
    async componentDidMount(){
        let list = await getNewAll();//获取侧边栏
        if(list.result.length){
            this.setState({
                navList : list.result
            })
        }
    }
    render() {
        const {titleinfo,navList,navId} = this.state;
        return (
            <div className="news">
                <Row type="flex">
                    <Col span={6}>
                        {/* <LeftSide titleinfo="实验室简介"></LeftSide> */}
                        {/* 左边导航 */}
                        <div className="leftSide">
                            <div className="leftSide-header">
                                {titleinfo}
                            </div>
                            <ul className="leftSide-container">
                                {
                                    !!navList.length && navList.map((item, index) => {
                                        return (
                                            <li className={`curp ${item.id === navId ? "active" : null}`} key={index} onClick={this.changeNav.bind(this, item, index)}>{item.category}</li>

                                            // <li className={item.id === navId ? "active" : null} key={index} onClick={this.changeNav.bind(this, item, index)}>{item.category}</li>
                                        )
                                    })
                                }
                            </ul>
                        </div>
                    </Col>

                    <Col span={18}>
                        <Switch>
                            <Route path='/news/30' exact component={ListPage} />
                            <Route path='/news/30/info'  component={ListInfo} />
                            <Route path='/news/31' exact component={ListPage} />
                            <Route path='/news/31/info'  component={ListInfo} />
                            <Route path='/news/32' exact component={ListPage} />
                            <Route path='/news/32/info'  component={ListInfo} />
                            <Route path='/news/33' exact component={ListPage} />
                            <Route path='/news/33/info'  component={ListInfo} />
                        </Switch>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default News;