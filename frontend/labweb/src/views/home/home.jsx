import React, { Component } from "react";
import MoreTitle from "@components/home/moreTitle";
import './home.less';
import { Row, Col,Carousel ,Typography } from "antd";
import {getHome,getSlideshow} from '@/api'

const { Paragraph } = Typography;

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      slideList: [],
      homeList:[]
    };
  }
  async componentDidMount(){
    let slide = await getSlideshow();
    let home = await getHome();
    this.setState({
      slideList : slide.result || [],
      homeList:home.result || []
    })
  }
  jump=(data)=>{
    let url = `/news/30/info`
    let childList = {
        navId:data.id,
        titleinfo:data.title
    }
        this.props.history.push(`${url}`,childList);
    }
  render() {
     const {slideList,homeList} = this.state;
     let [list1=[],list2=[],list3=[],list4=[],list5=[],list6=[],list7=[],list8=[],list9=[]] = homeList;
    return (
      <div className="home">
        <div className="home-more">
          <Row type="flex" style={{ height: "400px" }}>
            <Col className="home-left">
              <Carousel  autoplay>
               
                {
                    slideList.map((item,index)=>{
                       return(
                        <div key={index}>
                          <img src={item.imageurl} width="660px" alt="轮播图"  onClick={this.jump.bind(this,item)} className="curp"/>
                          <div className="silde-title" >
                            <Paragraph ellipsis style={{width:"500px"}}>{item.title}</Paragraph>
                          </div>
                    </div>
                       )
                    })
                  }
              </Carousel>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo="科研动态" datalist={list1} type="1"/>
            </Col>
          </Row>
          <Row type="flex">
            <Col className="home-left">
              <MoreTitle titleinfo="新闻动态"  datalist={list2} imglist={list3}  type="2"/>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo="通知公告" datalist={list4}  type="3"/>
            </Col>
          </Row>
          <Row type="flex">
            <Col className="home-left">
              <MoreTitle titleinfo="学术活动" datalist={list5} imglist={list6}  type="2"/>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo="招聘招生" datalist={list7}   type="3"/>
            </Col>
          </Row>
        </div>
        <div className="home-more">
          <Row type="flex">
              <Col span={24}>
                <MoreTitle titleinfo="资源发布" datalist={list8}  type="4"/>
              </Col>
            </Row>
        </div>
        <div className="home-more">
          <Row type="flex">
              <Col span={24}>
                <MoreTitle titleinfo="友情链接" datalist={list9}  type="5"/>
              </Col>
            </Row>
        </div>
      </div>
    );
  }
}

export default Home;
