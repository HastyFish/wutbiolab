import React, { Component } from 'react'
import { Row, Col, Menu } from 'antd';
import { Link, withRouter } from 'react-router-dom'

import './index.less'
import LogoPic from './image/logo-pic.png'
import LogoInfo from './image/logo-info.png'

class Header extends Component {
  render() {
    let path = this.props.location.pathname;
    if(path.indexOf('/introduction')===0 ){
      path = '/introduction';
    }else if(path.indexOf('/news')===0){
      path = '/news';
    }else if(path.indexOf('/notices')===0){
      path = '/notices';
    }else if(path.indexOf('/resources')===0){
      path = '/resources';
    }else if(path.indexOf('/scientific')===0){
      path = '/scientific';
    }
    return (
      <div className="publicHeader">
        <div className = "header-top">
            <img src={LogoPic} alt="logo" className="logo" />
            <img src={LogoInfo} className="logo-info" alt="武汉理工大学智能生物信息实验室" />
            <p>武汉理工大学智能生物信息实验室</p>
        </div>
            
        <Row className='header-nav'>
          <Col>
            <Menu
              mode="horizontal"
              defaultSelectedKeys={['/']}
              selectedKeys={[path]}
            >
              <Menu.Item key="/">
                <Link to="/">
                  <span>首页</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="/introduction">
                <Link to="/introduction/1">
                  <span>实验室简介</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="/news">
                <Link to="/news/30">
                  <span>新闻动态</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="/notices">
                <Link to="/notices/34">
                  <span>通知公告</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="/scientific">
                <Link to="/scientific/10">
                  <span>科研工作</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="/resources">
                <Link to="/resources/37">
                  <span>资源发布</span>
                </Link>
              </Menu.Item>
            </Menu>
          </Col>
        </Row>
      </div>

    );
  }
}

export default withRouter(Header);