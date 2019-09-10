import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';
import { Layout } from 'antd';
import storageUtils from '../../utils/storageUtils';

import LeftNav from '../../components/left-nav/left-nav';
import MyHeader from '../../components/header/header';
import NotFound from '../notfound/notfound';

import Home from '../home/home';

import './index.less';

const { Header,  Sider, Content } = Layout;

export default class Admin extends Component {
  componentDidMount(){
    const user = storageUtils.getUser() || {};
    const token = storageUtils.getToken();
    if(!user || !user.username || !token) {
      // 自动跳转到登陆(在render()中)
      // return <Redirect to='/login'/>
      this.props.history.replace('/login')
    }
  }

  render() {
    return (
      // <Switch>
      //   <Route path='/home' component={Home} />
      //   <Redirect to='/home' />
      // </Switch>
      <Layout className='pageLayout'>
        <Header className='header' style={{ 
            backgroundColor: '#fff',
          }}>
          <MyHeader />
        </Header>
        <Layout className='body'>
          <Sider className='sider'>
            <LeftNav />
          </Sider>
          <Content className='content'>
            <Switch>
              <Route path='/home' component={Home} />
              {/* <Redirect to='/user' /> */}
              <Route component={NotFound}></Route>
            </Switch>
          </Content>
        </Layout>
      </Layout>
    )
  }
}