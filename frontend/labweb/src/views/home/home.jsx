import React, { Component } from "react";
import MoreTitle from "@components/home/moreTitle";
import './home.less';
import { Row, Col,Carousel ,Typography } from "antd";
import {getHome,getSlideshow} from '@/api'
import  {NavTitle,NewsTitle,LinksTitle,ResourcesTitle} from "@utils/titleConfig"

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
    let url = `/news/30/info/${data.id}`
    let childList = {
        navId:data.id,
        titleinfo:data.title
    }
        this.props.history.push(`${url}`,childList);
    }
  render() {
     const {slideList,homeList} = this.state;
     let [list1=[],list2=[],list3=[],list4=[],list5=[],list6=[]] = homeList;
    return (
      <div className="home">
        <div className="home-more">
          <Row type="flex" style={{ height: "300px" }}>
            <Col className="home-left">
              <Carousel  autoplay>
                {
                    slideList.map((item,index)=>{
                       return(
                        <div key={index}>
                          <img src={item.imageurl} alt="轮播图" width="430px" onClick={this.jump.bind(this,item)} className="curp"/>
                          <div className="silde-title" >
                            <Paragraph ellipsis style={{width:"400px"}}>{item.title}</Paragraph>
                          </div>
                    </div>
                       )
                    })
                  }
              </Carousel>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo={NavTitle[4].en} datalist={list1} type="1"/>
            </Col>
          </Row>
          <Row type="flex">
            <Col className="home-left1">
              <MoreTitle titleinfo={NewsTitle[1].en}  datalist={list3} imglist={list2}  type="2"/>
            </Col>
            <Col className="home-right1">
              <MoreTitle titleinfo={ResourcesTitle[3].en} datalist={list4}  type="3"/>
            </Col>
          </Row>
        </div>
        <div className="home-more">
          <Row type="flex">
              <Col span={24}>
                <MoreTitle titleinfo={NavTitle[3].en} datalist={list5}  type="4"/>
              </Col>
            </Row>
        </div>
        <div className="home-more">
          <Row type="flex">
              <Col span={24}>
                <MoreTitle titleinfo={LinksTitle[0].en} datalist={list6}  type="5"/>
              </Col>
            </Row>
        </div>
      </div>
    );
  }
}

export default Home;
