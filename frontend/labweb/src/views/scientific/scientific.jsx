
import React, { Component } from 'react';
import './scientific.less';
import { Row, Col } from 'antd';
import { Switch, Route } from 'react-router-dom';
import ListInfo from '@components/rightSide/listInfo';
// import ListPage from '@components/rightSide/listPage';
import SciTable from '@components/rightSide/sciTable';
import OnlyInfo from '@components/rightSide/onlyInfo';
// import TableInfo from '@components/rightSide/tableInfo';

import  {NavTitle} from "@utils/titleConfig"

import {getSciAll} from '@/api'


class Scientific extends Component {
    constructor(props) {
        super(props);
        this.state = {
            titleinfo : NavTitle[2].en,
            navList : [],
            dataList:[],
            navId:10,
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
        this.props.history.push(`/scientific/${item.id}`,childList);
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
        let list = await getSciAll();//获取侧边栏
        if(list.result.length){
            this.setState({
                navList : list.result
            })
        }
    }
    render() {
        const {titleinfo,navList,navId} = this.state;
        return (
            <div className="scientific">
                <Row type="flex">
                    <Col span={6}>
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
                            <Route path='/scientific/2' exact component={OnlyInfo} />
                            <Route path='/scientific/10' exact component={SciTable} />
                            <Route path='/scientific/10/info'  component={ListInfo} />
                            <Route path='/scientific/12' exact component={SciTable} />
                            <Route path='/scientific/12/info'  component={ListInfo} />
                            {/* <Route path='/scientific/11' exact component={ListPage} />
                            <Route path='/scientific/11/info'  component={ListInfo} /> */}
                        </Switch>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Scientific;