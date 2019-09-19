import React, { Component } from "react";
import MoreTitle from "@components/home/moreTitle";
import './home.less';
import { Row, Col,Carousel ,Typography } from "antd";
// import {getHotNews,getCooperation,getSlideshow,getProductAll,getStrength} from '@api/';
import ac from './11.jpg';
const { Paragraph } = Typography;

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      slideList: [],
    };
  }
  // async componentDidMount(){
  //   let slide = await getSlideshow();
  //   let product = await getProductAll();
  //   let strength = await getStrength();
  //   let news = await getHotNews();
  //   let Cooperation = await getCooperation();
  //   this.setState({
  //     slideList : slide.result ? JSON.parse(slide.result) :[],
  //     productList : product.result,
  //     newsList : news.result,
  //     strengthList : strength.result,
  //     CooperationList : Cooperation.result ?  JSON.parse(Cooperation.result) : [],
  //   })
  // }
  render() {
    // const {slideList,productList,newsList,strengthList,CooperationList} = this.state;
    return (
      <div className="home">
        <div className="home-more">
          <Row type="flex" style={{ height: "400px" }}>
            <Col className="home-left">
              <Carousel  autoplay>
                <div >
                  <div>
                    <img src={ac} width="660px" alt=""/>
                    <Paragraph ellipsis style={{width:"500px"}}>ParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraph</Paragraph>
                  </div>
                </div>
                <div >
                  <div>
                    <img src={ac} width="660px" alt=""/>
                    <Paragraph ellipsis style={{width:"500px"}}>ParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraphParagraph</Paragraph>
                  </div>
                </div>
              </Carousel>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo="科研动态" type="1"/>
            </Col>
          </Row>
          <Row type="flex">
            <Col className="home-left">
              <MoreTitle titleinfo="新闻"  type="2"/>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo="通知公告"  type="3"/>
            </Col>
          </Row>
          <Row type="flex">
            <Col className="home-left">
              <MoreTitle titleinfo="学术活动"  type="2"/>
            </Col>
            <Col className="home-right">
              <MoreTitle titleinfo="招聘招生"  type="3"/>
            </Col>
          </Row>
        </div>
        <div className="home-more">
          <Row type="flex">
              <Col span={24}>
                <MoreTitle titleinfo="资源发布"  type="4"/>
              </Col>
            </Row>
        </div>
        <div className="home-more">
          <Row type="flex">
              <Col span={24}>
                <MoreTitle titleinfo="友情链接"  type="5"/>
              </Col>
            </Row>
        </div>
      </div>
    );
  }
}

export default Home;
