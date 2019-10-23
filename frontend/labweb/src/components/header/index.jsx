import React, { Component } from 'react'
import { Row, Col, Menu } from 'antd';
import { withRouter } from 'react-router-dom'

import './index.less'
import LogoPic from './image/logo-pic.png'
import LogoInfo from './image/logo-info.png'
import  {NavTitle} from "@utils/titleConfig"

class Header extends Component {
  jump=(val,type)=>{
    let url = this.props.location.pathname;
    switch(type){
      case 2:
          if(!url.includes("/introduction")){
            this.props.history.push(val)
          }
      break;
      case 5:
          if(!url.includes("/scientific")){
            this.props.history.push(val)
          }
      break;
      default:this.props.history.push(val);
    }
  }
  render() {
    let path = this.props.location.pathname;
    if(path.indexOf('/introduction')===0 ){
      path = '/introduction';
    }else if(path.indexOf('/news')===0){
      path = '/news';
    }else if(path.indexOf('/contact')===0){
      path = '/contact';
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
              <Menu.Item key="/"  onClick={this.jump.bind(this,"/",1)}>
                  <span className="nav-url">{NavTitle[0].en}</span>
              </Menu.Item>
              <Menu.Item key="/introduction" onClick={this.jump.bind(this,"/introduction/1",2)}>
                  <span className="nav-url">{NavTitle[1].en}</span>
              </Menu.Item>
              <Menu.Item key="/scientific" onClick={this.jump.bind(this,"/scientific/2",3)}>
                  <span className="nav-url">{NavTitle[2].en}</span>
              </Menu.Item>
              <Menu.Item key="/resources" onClick={this.jump.bind(this,"/resources/39",4)}>
                  <span className="nav-url">{NavTitle[3].en}</span>
              </Menu.Item>
              <Menu.Item key="/news" onClick={this.jump.bind(this,"/news/30",5)}>
                  <span className="nav-url">{NavTitle[4].en}</span>
              </Menu.Item>
              <Menu.Item key="/contact" onClick={this.jump.bind(this,"/contact/13",6)}>
                  <span className="nav-url">{NavTitle[5].en}</span>
              </Menu.Item>
            </Menu>
          </Col>
        </Row>
      </div>

    );
  }
}

export default withRouter(Header);