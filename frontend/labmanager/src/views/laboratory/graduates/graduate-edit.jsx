import React,{Component} from 'react';
import propTypes from 'prop-types';
import {
  Select,
  Input,
  Button,
  Form,
  message,
  DatePicker,
} from 'antd';
import moment from 'moment';
import locale from 'antd/es/date-picker/locale/zh_CN';

import BraftEditor from '@/components/rich-text-editor/rich-text-editor';

import {reqGraduatesTypes, reqGraduateData, reqSaveGraduate, reqPublishGraduate} from '@/api';

import {formateDate} from '@/utils/dateUtils'

import './graduates.less';
import 'moment/locale/zh-cn';

moment.locale('zh-cn');

const {Item} = Form;
const {Option} = Select;

class GraduatesEdit extends Component{
  static propTypes = {
    id: propTypes.string
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
          categoryList:[]
        }
    }else{
      //说明是新增
      this.state = {
        newItem:{
        },
        categoryList:[]
      }
    }
  }

  //限制发布时间小于等于今天
  disabledDate(current) {
    return current && current > moment().endOf('day');
  }

  //保存或发布人员
  saveOrPublishNews = (type) => {
    //首先进行表单验证，验证通过了再进行保存和发布的处理
    this.props.form.validateFields( async (error, values) => {
      if(!error){
        //1. 收集数据,封装成new对象
        const {title, graduateCategoryId, publishDate} = values;

        //获取富文本
        const context = this.editor.current.getContext();

        //判断为新增还是编辑
        const {isUpdate} = this;
        //请求参数对象
        let param = {title, graduateCategoryId, categoryId : 4, context, publishDate:Date.parse( new Date(publishDate._d))};
        if(isUpdate){
          //编辑更新,需要获取当前Id
          const {id} = this.state;
          //构建新对象的值
          param.id = id;
        }else{
          //新增操作不携带id
        }
        //判断是保存还是发布
        let result;
        if(type === 'save'){
          //发送请求
          result = await reqSaveGraduate(param);
        }else{
          result = await reqPublishGraduate(param);
        }
        if(result.code === 0){
          //发送请求成功
          message.success(`${type === 'save'?'保存':'发布'}成功`);
          //路由跳转
          setTimeout(() => {
            this.props.history.replace('/laboratory/graduates');
          },100)
        }else{
          message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
        }
        
      }else{
        //提示错误
        message.error('表单验证不通过，请检查!');
        //如果富文本及照片墙已经填写则保留
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
    //获取实验室风采类型列表
    const result = await reqGraduatesTypes();
    if(result.code === 0){
      const categoryList = result.result;
      this.setState({
        categoryList
      })
      const {id} = this.state;
      if(id){
        const result = await reqGraduateData(id);
        if(result.code === 0){
          //携带新闻的参数跳入新闻编辑页面
          const newItem = result.result;
          this.setState({
            newItem
          })
          const {graduateCategoryId} = newItem;
          graduateCategoryId && this.props.form.setFieldsValue({'graduateCategoryId':graduateCategoryId})
        }else{
          message.error('获取实验室风采信息失败，请稍后再试!');
        }
      }else{
        //select框赋值
        this.props.form.setFieldsValue({'graduateCategoryId':categoryList[0].id})
      }
    }else {
      message.error('获取实验室风采类型失败, 请稍后重试')
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
      <div className='graduate'>
        <div className="graduate-edit-title" >
          实验室风采编辑
        </div>
        <div className="graduate-body">
          <div className="graduate-content">
            <Form>
              <Item label="类型选择" {...formItemLayout}>
                {
                  getFieldDecorator('graduateCategoryId', {
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
              <Item label="标题" {...formItemLayout}>
                {
                  getFieldDecorator('title', {
                    initialValue: title,
                    rules: [
                      {required: true, message: '必须指定标题'},
                      {whitespace: true, message: '请输入正确文字内容'},
                    ]
                  })(
                    <Input placeholder="请输入标题"/>
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
                    <DatePicker disabledDate={this.disabledDate} style={{width: '100%'}} locale={locale} showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}/>
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
      </div>
    )
  }
}


export default Form.create()(GraduatesEdit)