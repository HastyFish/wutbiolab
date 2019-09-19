import React, { Component } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { Layout } from 'antd';
import storageUtils from '@/utils/storageUtils';

import LeftNav from '@/components/left-nav/left-nav';
import MyHeader from '@/components/header/header';
import NotFound from '@/views/notfound/notfound';

import Home from '@/views/home/home';
import Laboratory from '@/views/laboratory/laboratory';
import News from '@/views/news/news';
import EditNews from '@/views/news/add-edit-news';
import Notice from '@/views/notice/notice';
import EditNotice from '@/views/notice/add-edit-notice';
import Source from '@/views/source/source';
import EditSource from '@/views/source/add-edit-source';
import Science from '@/views/science/science';

import './index.less';

const { Header,  Sider, Content } = Layout;

export default class Admin extends Component {

  // componentWillMount(){
  //   const user = storageUtils.getUser() || {};
  //   const token = storageUtils.getToken();
  //   if(!user || !user.username || !token) {
  //     // 自动跳转到登陆(在render()中)
  //     this.props.history.replace('/login')
  //   }
  // }


  render() {
    const user = storageUtils.getUser() || {};
    const token = storageUtils.getToken();
    if(!user || !user.username || !token) {
      // 自动跳转到登陆(在render()中)
      return <Redirect to='/login'/>
    }
    return (
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
              <Route path='/home' exact component={Home} />
              <Route path='/laboratory' component={Laboratory} />
              <Route path='/news' exact component={News} />
              <Route path='/news/edit' component={EditNews}/>
              <Route path='/notice' exact  component={Notice} />
              <Route path='/notice/edit' component={EditNotice} />
              <Route path='/source' exact  component={Source} />
              <Route path='/source/edit'  component={EditSource} />
              <Route path='/science'  component={Science} />
              <Redirect to='/home' />
              <Route component={NotFound}></Route>
            </Switch>
          </Content>
        </Layout>
      </Layout>
    )
  }
}