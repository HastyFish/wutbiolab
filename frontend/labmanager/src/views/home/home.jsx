import React, {Component} from 'react';
import {Switch, Route, Redirect} from 'react-router-dom';
import {
  Tabs,
  Button,
  message
} from 'antd';
// import {reqHomePublish} from '../../api';

// import Banner from './banner/banner';
// import Product from './product/product';
// import Server from './server/server';
// import Partner from './partner/partner';
import './home.less';

const {TabPane} = Tabs

export default class Home extends Component{

  publish = async () => {
    // const result = await reqHomePublish();
    // if(result.code === 0){
    //   message.success('发布成功');
    // }else{
    //   message.error('发布失败，请稍后再试');
    // }
  }

  render(){
    return (
      <div className='home'>
        <div className='home-title'>
          <span>首页资讯</span>
          <Button type='primary' style={{float:'right', marginRight:20, marginTop:12}} onClick={this.publish}>发布</Button>
        </div>
        <div className='home-body'>
        <Tabs activeKey={this.props.history.location.pathname} onChange={(key) => this.props.history.push(key)}>
          <TabPane tab="轮播图" key="/home">
          </TabPane>
          <TabPane tab="四大产品" key="/home/product">
          </TabPane>
          <TabPane tab="高效服务及优势" key="/home/server">
          </TabPane>
          <TabPane tab="合作伙伴" key="/home/partner">
          </TabPane>
        </Tabs>
        <div className='home-content'>
          Home
          {/* <Switch>
            <Route path='/home' exact component={Banner} />
            <Route path='/home/product' component={Product} />
            <Route path='/home/server' component={Server} />
            <Route path='/home/partner' component={Partner} />
            <Redirect to='/home' />
          </Switch> */}
        </div>
        </div>
      </div>
    )
  }
}