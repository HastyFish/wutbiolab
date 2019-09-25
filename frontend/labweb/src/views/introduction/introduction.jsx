import React, { Component } from 'react';
import './introduction.less';
import { Row, Col } from 'antd';
import { Switch, Route } from 'react-router-dom';
import OnlyInfo from '@components/rightSide/onlyInfo';
import ListPage from '@components/rightSide/listPage';
import ListInfo from '@components/rightSide/listInfo';
import TablePage from '@components/rightSide/tablePage';
import TableInfo from '@components/rightSide/tableInfo';

import  {NavTitle} from "@utils/titleConfig"

import {getLabAll} from '@/api'
import { Number } from 'core-js';


class Introduction extends Component {
    constructor(props) {
        super(props);
        this.state = {
            titleinfo : NavTitle[1].en,
            navList : [],
            dataList:[],
            navId:1,
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
        this.props.history.push(`/introduction/${item.id}`,childList);
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
        let list = await getLabAll();//获取侧边栏
        if(list.result.length){
            this.setState({
                navList : list.result
            })
        }
        // this.getOne(1);
    }
    render() {
        const {titleinfo,navList,navId} = this.state;
        return (
            <div className="Introduction">
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
                            <Route path='/introduction/1' component={OnlyInfo} />
                            <Route path='/introduction/2' component={OnlyInfo} />
                            <Route path='/introduction/3' exact component={TablePage} />
                            <Route path='/introduction/3/info'  component={TableInfo} />
                            <Route path='/introduction/4' exact component={ListPage} />
                            <Route path='/introduction/4/info' exact component={ListInfo} />
                            <Route path='/' component={OnlyInfo} />
                        </Switch>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Introduction;