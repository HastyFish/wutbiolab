import React, { Component } from 'react';
import { getFooter } from '@api'
import {Row, Col } from 'antd'
import './index.less'
import Logo from './image/logo.png'
class Footer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            footerList:[]
        }
    }
    getNode = (data)=>
        data.map((item,index) => {
            return(
                <p  key={index} dangerouslySetInnerHTML={{ __html: item.context ? JSON.parse(item.context).context : ""}}></p>
            )
        })
    async componentDidMount(){
        let data = await getFooter();
        if(data.result && data.result.length){
           this.setState({
            footerList :data.result
           })
        }
    }
    render() {
        const {footerList} = this.state;
        return (
            <div className="footer">
                <Row type="flex" justify="space-between" className="footer-content">
                   <Col span={12} className='footer-logo'>
                       <img src={Logo} alt="武汉理工大学计算机科学与技术学院"/>
                       <div className="foot-info">
                            <span>武汉理工大学</span>
                            <span>计算机科学与技术学院</span>
                       </div>   
                   </Col>
                   {/* <Divider type="vertical" /> */}
                   <Col span={12} className='footer-info'>
                     {this.getNode(footerList)}
                   </Col>
                </Row>
            </div>
        );
    }
}

export default Footer;