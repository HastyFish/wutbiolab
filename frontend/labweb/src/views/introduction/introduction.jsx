import React, { Component } from 'react';
import './introduction.less';
import { Row, Col,Breadcrumb } from 'antd';
import { Switch, Route } from 'react-router-dom';
import LeftSide from '@components/leftSide';
import RightSide from '@components/rightSide';
import OnlyInfo from '@components/rightSide/onlyInfo';
import ListPage from '@components/rightSide/listPage';
import TablePage from '@components/rightSide/tablePage';
import NextPage from '@components/rightSide/nextPage';


import {getLabAll,getLabOneById} from '@/api'
import { Number } from 'core-js';

const navOrder = [1,2,3,4];

class Introduction extends Component {
    constructor(props) {
        super(props);
        this.state = {
            titleinfo : "实验室简介",
            navList : [],
            dataList:[],
            navId:1,
            navName:""
        }
    }
    /**
     * 切换导航
     */
    changeNav=(item,index)=>{
        debugger;
        this.setState({
            navId:item.id,
            navName:item.category
        })
        let childList = {
            navId:item.id,
            navName:item.category,
            titleinfo:this.state.titleinfo
        }
        this.props.history.push(`/introduction/${item.id}`,childList);
        
        // if([1,2].includes(item.id)){
        //     this.getOne(item.id)
        // }else{
        //     this.getList(item.id)
        // }
    }
    // //获取一段文字
    //  getOne = async(id)=>{
    //     let data = await getLabOneById(id);
    //     if(data.result && data.result.length){
    //         this.setState({
    //             dataList : data.result
    //         })
    //     }
    // }
    // //获取列表
    // getList= async(id)=>{
    //     let list = await getLabOneById(id);
    //     if(list.result && list.result.length){
    //         this.setState({
    //             dataList : list.result
    //         })
    //     }
    // }
    componentWillMount(){
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length-1]) ;
        let childList = {
            navId,
            navName:"",
            titleinfo:this.state.titleinfo
        }
        this.setState({
            navId
        })
        // this.props.history.push(`/introduction`,childList);
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
        const {titleinfo,navList,navId,navName} = this.state;
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
                                            <li className={item.id === navId ? "active" : null} key={index} onClick={this.changeNav.bind(this, item, index)}>{item.category}</li>
                                        )
                                    })
                                }
                            </ul>
                        </div>
                    </Col>

                    <Col span={18}>
                        <Switch>
                            {/* <Route path='/introduction' component={RightSide} /> */}
                            <Route path='/introduction/1' component={OnlyInfo} />
                            <Route path='/introduction/2' component={OnlyInfo} />
                            <Route path='/introduction/3' exce component={TablePage} />
                            <Route path='/introduction/4' component={ListPage} />
                             {/* <Route path='/' component={RightSide} /> */}
                        </Switch>
                        {/* 右边内容 */}
                        {/* <div className="rightSide">
                            {
                            [1,2].includes(navId) && <OnlyInfo navId={navId} titleinfo={titleinfo} navName={navName}></OnlyInfo>
                            }
                            {
                                navId === 3 && <TablePage  navId={navId} titleinfo={titleinfo} navName={navName}></TablePage>
                            }
                            {
                                navId===4 && <ListPage  navId={navId} titleinfo={titleinfo} navName={navName}></ListPage>
                            }
                        </div> */}
                        
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Introduction;