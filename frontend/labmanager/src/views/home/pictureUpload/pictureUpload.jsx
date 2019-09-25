import React,{Component} from 'react';
import {
  Row,
  Col,
  Button,
  message
} from 'antd';
import {reqImageList, reqPublishImg} from '@/api';
import PicturesWall from '@/components/picture-wall/pictures-wall'


class PictureUpload extends Component {

  constructor(props){
    super(props);

    // 创建用来保存ref标识的标签对象的容器
    this.newp = React.createRef()
    this.scip = React.createRef()

    //设置state
    this.state = {
      newsImage : null,  //新闻配图列表
      academicImage : null,   //学术活动配图
    }
  }

  uploadImg = async () => {
    //获取图片
    const newsImage = JSON.stringify(this.newp.current.getImgs());
    const academicImage = JSON.stringify(this.scip.current.getImgs());
    //发送请求
    const result = await reqPublishImg({newsImage, academicImage});
    if(result.code === 0){
      message.success('发布图片成功')
    }else{
      message.error('发布图片失败, 请稍后再试')
    }
  }

  async componentDidMount(){
    //发送请求获取配图
    const result = await reqImageList();
    if(result.code === 0){
      let {newsImage,academicImage} = result.result;
      newsImage && (newsImage = JSON.parse(newsImage));
      academicImage && (academicImage = JSON.parse(academicImage));
      this.setState({
        newsImage,academicImage
      })
    }else{
      message.error('获取图片失败, 请稍后再试')
    }
  }

  render(){
    const {newsImage,academicImage} = this.state;

    return (
      <div>
        <Row>
          <Col span={12}>
            <p className="subtitle">新闻动态配图</p>
            <div className="subcontent">
              {newsImage ? <PicturesWall ref={this.newp} option={{width:280,height:200}} image = {newsImage} /> : null}
              {!newsImage ? <PicturesWall ref={this.newp} option={{width:280,height:200}} image = {[]} /> : null}
            </div> 
          </Col>
          <Col span={12}>
            <p className="subtitle">学术活动配图</p>
            <div className="subcontent">
              {academicImage ? <PicturesWall ref={this.scip} option={{width:280,height:200}} image = {academicImage} /> : null}
              {!academicImage ? <PicturesWall ref={this.scip} option={{width:280,height:200}} image = {[]} /> : null}
            </div>
          </Col>
        </Row>
        <Row type="flex" justify="end">
          <Col>
            {/* <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.uploadImg}>保存</Button> */}
            <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.uploadImg}>发布</Button>
            <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={this.uploadImg}>取消</Button>
          </Col>
        </Row>
      </div>
    )
  }
}

export default PictureUpload