import React,{Component} from 'react';
import propTypes from 'prop-types';
import {
  Tabs,
  Input,
  Button,
  Form,
  message,
  DatePicker 
} from 'antd';
import moment from 'moment';
import locale from 'antd/es/date-picker/locale/zh_CN';

import BraftEditor from '@/components/rich-text-editor/rich-text-editor';

import {reqArticleData, reqSaveArticle, reqPublishArticle} from '@/api';
import {formateDate} from '@/utils/dateUtils'

import './article.less';
import 'moment/locale/zh-cn';

moment.locale('zh-cn');
const {TabPane} = Tabs;
const {Item} = Form;

class EditArticle extends Component{
  static propTypes = {
    newItem: propTypes.object
  }

  constructor(props){
    super(props)
    // 创建用来保存ref标识的标签对象的容器
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
        }
    }else{
      //说明是新增
      this.state = {
        newItem:{
        },
      }
    }
  }


  //保存或发布新闻
  saveOrPublishNews = (type) => {
    //首先进行表单验证，验证通过了再进行保存和发布的处理
    this.props.form.validateFields( async (error, values) => {
      if(!error){
        //1. 收集数据,封装成new对象
        const {title, periodicalName, author, publishYear,publishDate} = values;

        //获取富文本
        const context = this.editor.current.getContext();

        //判断为新增还是编辑
        const {isUpdate} = this;
        //请求参数对象
        let param = {title, periodicalName, author, publishYear, context, publishDate:Date.parse( new Date(publishDate._d)), categoryId: 10,};
        
        if(isUpdate){
          //编辑更新,需要获取当前Id
          const {id} = this.state;
          //构建新对象的值
          param.id = id;
        }else{
        }
        //判断是保存还是发布发送请求
        let result;
        if(type === 'save'){
          result = await reqSaveArticle(param);
        }else {
          result = await reqPublishArticle(param);
        }
        if(result.code === 0){
          //发送请求成功
          message.success(`${type === 'save'?'保存':'发布'}成功`);
          //路由跳转
          setTimeout(() => {
            this.props.history.replace('/science');
          },100)
        }else{
          message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
        }
      }else{
        //提示错误
        message.error('表单验证不通过，请检查!');
        //如果富文本及照片墙已经填写则保留
         //获取封面图片及富文本
         const {imageUrl,imageName} = this.pw.current.getImgs();
         const context = this.editor.current.getContext();
         const newItem = this.state.newItem
         newItem.context = context;
         newItem.imageName = imageName;
         newItem.imageUrl = imageUrl;
         this.setState({
          newItem
         })
      }
    })
  }

  //选择框变化事件
  handleChange = (e) => {
    this.setState({
      categoryType:e
    })
  }

  async componentDidMount(){
    const {id} = this.state;
    if(id){
      const result = await reqArticleData(id);
      if(result.code === 0){
        //携带新闻的参数跳入新闻编辑页面
        const newItem = result.result;
        this.setState({
          newItem
        })
      }else{
        message.error('获取新闻失败，请稍后再试!');
      }
    }
  }

  render(){
    const {newItem} = this.state;
    const {title, periodicalName, author, publishYear, context, publishDate} = newItem;
    // 指定Item布局的配置对象
    const formItemLayout = {
      labelCol: { span: 2 },  // 左侧label的宽度
      labelAlign:'left',
      wrapperCol: { span: 5 }, // 右侧包裹的宽度
    }
  
    const {getFieldDecorator} = this.props.form


    return (
      <div className='article'>
        <div className="article-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
            <TabPane tab="论文编辑" key="/science/edit">
            </TabPane>
          </Tabs>
        </div>
        <div className="article-body">
          <Form>
            <Item label="论文题目" {...formItemLayout}>
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
            <Item label="刊物名称" {...formItemLayout}>
              {
                getFieldDecorator('periodicalName', {
                  initialValue: periodicalName,
                  rules: [
                    {required: true, message: '必须指定刊物名称'},
                  ]
                })(
                  <Input placeholder="请输入刊物名称"/>
                )
              }
            </Item>
            <Item label="第一作者" {...formItemLayout}>
              {
                getFieldDecorator('author', {
                  initialValue: author,
                  rules: [
                    {required: true, message: '必须指定第一作者'},
                  ]
                })(
                  <Input placeholder="请输入第一作者"/>
                )
              }
            </Item>
            <Item label="发表年度" {...formItemLayout}>
              {
                getFieldDecorator('publishYear', {
                  initialValue: publishYear,
                  rules: [
                    {required: true, message: '必须指定发表年度'},
                  ]
                })(
                  <Input placeholder="请输入发表年度"/>
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


export default Form.create()(EditArticle)