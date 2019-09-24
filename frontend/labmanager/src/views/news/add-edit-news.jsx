import React,{Component} from 'react';
//import propTypes from 'prop-types';
import {
  Tabs,
  Select,
  Input,
  Button,
  Form,
  message,
  DatePicker 
} from 'antd';
import moment from 'moment';
import locale from 'antd/es/date-picker/locale/zh_CN';

import BraftEditor from '@/components/rich-text-editor/rich-text-editor';
//import PicturesWall from './pictures-wall';
import PicturesWall from '@/components/picture-wall/pictures-wall';

import {reqNewTypeList, reqNewItem, reqsavePublishNews} from '@/api';
import {formateDate} from '@/utils/dateUtils'

import './news.less';
import 'moment/locale/zh-cn';

moment.locale('zh-cn');
const {TabPane} = Tabs;
const {Item} = Form;
const {Option} = Select;


class EditNews extends Component{

  constructor(props){
    super(props)
    // 创建用来保存ref标识的标签对象的容器
    this.pw = React.createRef()
    this.editor = React.createRef()

    // 取出携带的state
    const id = this.props.location.state;  // 如果是添加则新闻id没值, 否则为编辑页面新闻id有值
    // 保存是否是更新的标识
    this.isUpdate = !!id
    if(this.isUpdate){//说明是更新
        this.state = {
          id,
          newItem:{
          },
          categoryList:[],  //新闻类型下拉列表
          categoryType:30  //默认选中新闻类型
        }
    }else{
      //说明是新增
      this.state = {
        newItem:{
        },
        categoryList:[],  //新闻类型下拉列表
        categoryType:30
      }
    }
  }


  //保存或发布新闻
  saveOrPublishNews = (type) => {
    //首先进行表单验证，验证通过了再进行保存和发布的处理
    this.props.form.validateFields( async (error, values) => {
      if(!error){
        //1. 收集数据,封装成new对象
        const {title, categoryId,publishDate} = values;

        //获取封面图片及富文本
        //判断是否有图片,获取封面图片
        let image;
        const {categoryType} = this.state;
        if(categoryType === 30){
          let imgList = this.pw.current.getImgs();
          if(imgList.length > 0 && imgList[0].url !== ''){
            //判定是否上传图片
            image = JSON.stringify(imgList);
          }else{
            message.error('必须上传封面图片');
            return;
          }

        }else{
          image = JSON.stringify([]);
        }
        const context = this.editor.current.getContext();

        //判断为新增还是编辑
        const {isUpdate} = this;
        //请求参数对象
        let param = {title, categoryId, image, context,publishDate:Date.parse( new Date(publishDate._d))};
        if(isUpdate){
          //编辑更新,需要获取当前Id
          const {id} = this.state;
          //构建新对象的值
          param.id = id;
          //判断是保存还是发布
          if(type==='save'){
            //编辑状态下的保存
            param.publishStatus = 0;
          }else{
            //编辑状态下的发布
            param.publishStatus = 1;
          }
        }else{
          //新增操作不携带id
          //判断是保存还是发布
          if(type==='save'){
            //新增状态下的保存
            param.publishStatus = 0;
          }else{
            //新增状态下的发布
            param.publishStatus = 1;
          }
        }
        //发送请求
        const result = await reqsavePublishNews(param);
        if(result.code === 0){
          //发送请求成功
          message.success(`${type === 'save'?'保存':'发布'}成功`);
          //路由跳转
          setTimeout(() => {
            this.props.history.replace('/news');
          },100)
        }else{
          message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
        }
      }else{
        //提示错误
        message.error('表单验证不通过，请检查!');
        //如果富文本及照片墙已经填写则保留
         //获取封面图片及富文本
         const image = JSON.stringify(this.pw.current.getImgs());
         const context = this.editor.current.getContext();
         const newItem = this.state.newItem
         newItem.context = context;
         newItem.image = image;
         this.setState({
          newItem
         })
      }
    })
  }

  //选择框变化事件
  handleChange = (e) => {
    //遍历categoryList找到对应的category
    const {categoryList} = this.state;
    let category = categoryList.find(item => item.id === e);
    this.setState({
      categoryType:category.id
    })
  }

  async componentDidMount(){
    //获取新闻类型下拉列表
    const list = await reqNewTypeList();
    if(list.code === 0){
        //携带新闻的参数跳入新闻编辑页面
        const categoryList = list.result;
        this.setState({
          categoryList
        })
        const {id} = this.state;
        if(id){
          const result = await reqNewItem(id);
          if(result.code === 0){
            //携带新闻的参数跳入新闻编辑页面
            const newItem = result.result;
            const {categoryId} = newItem;
            this.setState({
              newItem,
              categoryType:categoryId
            })
            //select框赋值
            this.props.form.setFieldsValue({'categoryId':categoryId})
          }else{
            message.error('获取新闻失败，请稍后再试!');
          }
        }else{
          //select框赋值
          this.props.form.setFieldsValue({'categoryId':categoryList[0].id})
        }
      }else{
        message.error('获取新闻类型失败，请稍后再试!');
      }
  }

  render(){
    const {newItem,categoryList,categoryType} = this.state;
    const {title, image, context,publishDate} = newItem;
    // 指定Item布局的配置对象
    const formItemLayout = {
      labelCol: { span: 2 },  // 左侧label的宽度
      labelAlign:'left',
      wrapperCol: { span: 5 }, // 右侧包裹的宽度
    }
    const {getFieldDecorator} = this.props.form


    return (
      <div className='news'>
        <div className="new-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
            <TabPane tab="新闻编辑" key="/news/edit">
            </TabPane>
          </Tabs>
        </div>
        <div className="new-body">
          <Form>
            <Item label="编辑类型" {...formItemLayout}>
              {
                getFieldDecorator('categoryId', {
                  rules: [
                    {required: true, message: '必须指定分类'},
                  ]
                })(
                  <Select onChange={this.handleChange}>
                    {
                      categoryList.map(item => {
                        return (
                          <Option value={item.id} key={item.id}>{item.category}</Option>
                        )
                      })
                    }
                  </Select>
                )
              }
            </Item>
            <Item label="新闻标题" {...formItemLayout}>
              {
                getFieldDecorator('title', {
                  initialValue: title,
                  rules: [
                    {required: true, message: '必须指定新闻标题'},
                  ]
                })(
                  <Input placeholder="请输入新闻标题"/>
                )
              }
            </Item>
            <Item label="发布时间" {...formItemLayout}>
              {
                getFieldDecorator('publishDate', {
                  initialValue:publishDate ? moment(formateDate(publishDate), "YYYY-MM-DD HH:mm:ss") : null,
                  rules: [
                    {required: true, message: '必须指定发布时间!'},
                  ]
                })(
                  <DatePicker style={{width: '100%'}} locale={locale} showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}/>
                )
              }
            </Item>
            {
              categoryType === 30 ? (
              <Item label="封面上传">
                {image ? <PicturesWall ref={this.pw} option={{width:430,height:280}} image = {JSON.parse(image)} /> : null}
                {!image ? <PicturesWall ref={this.pw} option={{width:430,height:280}} image = {[]} /> : null}
              </Item>
              ):null
            }
            
            <Item>
              {
                context?<BraftEditor  ref={this.editor} context={context}></BraftEditor>:null
              }
              {
                !context?<BraftEditor  ref={this.editor} context={''}></BraftEditor>:null
              }
              
              {/* <RichTextEdit ref={this.editor} context={context} changeRichText = {(context) => this.setState({context})}/> */}
            </Item>
            <Item>
              <Button type='primary' style={{width:180,height:40,cursor:'pointer'}} onClick={() => this.saveOrPublishNews('save')}>保存</Button>
              <Button type='primary' style={{margin:'0 20px',width:180,height:40,cursor:'pointer'}} onClick={() => this.saveOrPublishNews('publish')}>发布</Button>
              <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={() => this.props.history.goBack()}>取消</Button>
            </Item>
          </Form>
        </div>
      </div>
    )
  }
}


export default Form.create()(EditNews)