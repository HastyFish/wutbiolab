
import React, { Component } from 'react';
import './resources.less';
import { Row, Col } from 'antd';
import { Switch, Route } from 'react-router-dom';
import ListInfo from '@components/rightSide/listInfo';
import ListPage from '@components/rightSide/listPage';
import  {NavTitle} from "@utils/titleConfig"


import {getResourceAll} from '@/api'


class Resources extends Component {
    constructor(props) {
        super(props);
        this.state = {
            titleinfo : NavTitle[5].en,
            navList : [],
            dataList:[],
            navId:37,
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
        this.props.history.push(`/resources/${item.id}`,childList);
    }
    componentWillMount(){
        let pathnameList = this.props.location.pathname.split("/");
        let navId = 0 ;
        if(pathnameList[pathnameList.length-2] === "info"){
            navId = Number(pathnameList[pathnameList.length-3])
        }else{
            navId = Number(pathnameList[pathnameList.length-1])
        }
        this.setState({
            navId
        })
    }
    //初始页面数据 默认第一条数据
    async componentDidMount(){
        let list = await getResourceAll();//获取侧边栏
        if(list.result.length){
            this.setState({
                navList : list.result
            })
        }
    }
    render() {
        const {titleinfo,navList,navId} = this.state;
        return (
            <div className="resources">
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
                            <Route path='/Resources/37' exact component={ListPage} />
                            <Route path='/Resources/37/info'  component={ListInfo} />
                            <Route path='/Resources/38' exact component={ListPage} />
                            <Route path='/Resources/38/info'  component={ListInfo} />
                            <Route path='/Resources/39' exact component={ListPage} />
                            <Route path='/Resources/39/info'  component={ListInfo} />
                        </Switch>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Resources;