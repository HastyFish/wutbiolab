import React,{Component} from 'react';
import {
  Row,
  Col,
  Button
} from 'antd';

import PicturesWall from '@/components/picture-wall/pictures-wall'


class PictureUpload extends Component {

  constructor(props){
    super(props);

    // 创建用来保存ref标识的标签对象的容器
    this.newp = React.createRef()
    this.scip = React.createRef()

    //设置state
    this.state = {
      newsImageList : [],  //新闻配图列表
      sciImageList : [],   //学术活动配图
    }
  }

  componentDidMount(){
    //发送请求获取配图

  }

  render(){
    const {newsImageList,sciImageList} = this.state;

    return (
      <div>
        <Row>
          <Col span={12}>
            <p className="subtitle">新闻动态配图</p>
            <div className="subcontent">
              {newsImageList ? <PicturesWall ref={this.newp} imageList = {newsImageList} /> : null}
              {!newsImageList ? <PicturesWall ref={this.newp} imageList = {[]} /> : null}
            </div> 
          </Col>
          <Col span={12}>
            <p className="subtitle">学术活动配图</p>
            <div className="subcontent">
              {newsImageList ? <PicturesWall ref={this.scip} imageList = {sciImageList} /> : null}
              {!newsImageList ? <PicturesWall ref={this.scip} imageList = {[]} /> : null}
            </div>
          </Col>
        </Row>
        <Row type="flex" justify="end">
          <Col>
            <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.uploadImg}>保存</Button>
            <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.uploadImg}>发布</Button>
            <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={this.uploadImg}>取消</Button>
          </Col>
        </Row>
      </div>
    )
  }
}

export default PictureUpload