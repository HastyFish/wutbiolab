import React,{Component} from 'react';
import propTypes from 'prop-types';
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

import {reqAcademicCategory, reqAcademicData, reqSaveAcademic, reqPublishAcademic} from '@/api';
import {formateDate} from '@/utils/dateUtils'

import './academic.less';
import 'moment/locale/zh-cn';

moment.locale('zh-cn');
const {TabPane} = Tabs;
const {Item} = Form;
const {Option} = Select;


class EditAcademic extends Component{
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
          categoryList:[],
        }
    }else{
      //说明是新增
      this.state = {
        newItem:{
        },
        categoryList:[],
      }
    }
  }


  //保存或发布新闻
  saveOrPublishNews = (type) => {
    //首先进行表单验证，验证通过了再进行保存和发布的处理
    this.props.form.validateFields( async (error, values) => {
      if(!error){
        //1. 收集数据,封装成new对象
        const {title, academicCategoryId,publishDate} = values;

        //获取富文本
        const context = this.editor.current.getContext();

        //判断为新增还是编辑
        const {isUpdate} = this;
        //请求参数对象
        let param = {title, academicCategoryId, context, publishDate:Date.parse( new Date(publishDate._d)),categoryId: 11};
        if(isUpdate){
          //编辑更新,需要获取当前Id
          const {id} = this.state.newItem;
          //构建新对象的值
          param.id = id;
        }else{
        }
         //判断是保存还是发布发送请求
         let result;
         if(type === 'save'){
            result = await reqSaveAcademic(param);
         }else{
            result = await reqPublishAcademic(param);
         }
        if(result.code === 0){
          //发送请求成功
          message.success(`${type === 'save'?'保存':'发布'}成功`);
          //路由跳转
          setTimeout(() => {
            this.props.history.replace('/science/academic');
          },100)
        }else{
          message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
        }
      }else{
        //提示错误
        message.error('表单验证不通过，请检查!');
        //如果富文本已经填写则保留
         //获取富文本
         const context = this.editor.current.getContext();
         const newItem = this.state.newItem
         newItem.context = context;
         this.setState({
          newItem
         })
      }
    })
  }


  async componentDidMount(){
    //获取分类信息
    const result = await reqAcademicCategory();
    if(result.code === 0){
      const categoryList = result.result;
      this.setState({categoryList})
      const {id} = this.state;
      if(id){
        const result = await reqAcademicData(id);
        if(result.code === 0){
          //携带新闻的参数跳入新闻编辑页面
          const newItem = result.result;
          this.setState({
            newItem
          })
          const {academicCategoryId} = newItem;
          academicCategoryId && this.props.form.setFieldsValue({'academicCategoryId':academicCategoryId})
        }else{
          message.error('获取新闻失败，请稍后再试!');
        }
      }else{
        //select框赋值
        this.props.form.setFieldsValue({'academicCategoryId':categoryList[0].id})
      }
    }else {
      message.error('获取类型失败, 请稍后再试')
    }
    
  }

  render(){
    const {newItem,categoryList} = this.state;
    const {title, context, publishDate} = newItem;
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
            <TabPane tab="学术编辑" key="/science/academic/edit">
            </TabPane>
          </Tabs>
        </div>
        <div className="article-body">
          <Form>
          <Item label="编辑类型" {...formItemLayout}>
                  {
                    getFieldDecorator('academicCategoryId', {
                      rules: [
                        {required: true, message: '必须指定分类'},
                      ]
                    })(
                      <Select>
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
              <Button type='primary' style={{width:180,height:40}} onClick={() => this.saveOrPublishNews('save')}>保存</Button>
              <Button style={{margin:'0 20px',width:180,height:40}} onClick={() => this.props.history.goBack()}>取消</Button>
              <Button style={{width:180,height:40}} onClick={() => this.saveOrPublishNews('publish')}>发布</Button>
            </Item>
          </Form>
        </div>
      </div>
    )
  }
}


export default Form.create()(EditAcademic)