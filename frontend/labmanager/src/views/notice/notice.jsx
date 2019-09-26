import React,{Component} from 'react';
import {
  Tabs,
  Button,
  Table,
  Icon,
  Pagination,
  LocaleProvider,
  message
} from 'antd';

import {reqNoticeTypeList, reqNoticeList, reqDeleteNotice} from '@/api';
import {formateDate} from '@/utils/dateUtils';
import './notice.less';

import zhCN from 'antd/lib/locale-provider/zh_CN';    //antd组件国际化

const {TabPane} = Tabs;

export default class Notice extends Component{

  state = {
    total:0,   //总数目
    pageNum: 1,  //当前页码
    pageSize:10,  //每页条数
    dataSource:[
    ],  //新闻数据数组
    loading: false,  //表格数据加载时显示loading效果
    categoryList:[],
  }

  //当前页码变化的回调函数
  changePage = async (page) => {
    //console.log('页码发生变化');

    //显示loading
    this.setState({
      loading:true
    })
    const categoryId = this.categoryId;
    const result =  await reqNoticeList({pageNum:page,pageSize:this.state.pageSize,categoryId});
    //将页码重置为page，每页条数不变
    this.setState({
      pageNum: page,
      total:result.result.total,
      dataSource:result.result.list
    })
    //隐藏loading
    this.setState({
      loading:false
    })
  }

  //每页展示条数发生变化的回调函数
  onShowSizeChange = async (current, size) => {
    //显示loading
    this.setState({
      loading:true
    })

    //重新获取数据
    const categoryId = this.categoryId;
    const result = await reqNoticeList({pageNum:1,pageSize:size,categoryId});

    //将页码重置为1，每页条数为传进来的参数
    this.setState({
      pageNum: 1,
      pageSize:size,
      total:result.result.total,
      dataSource:result.result.list
    })

    //隐藏loading
    this.setState({
      loading:false
    })

  }


  //生成表格中类型筛选所需数组
  getCategoryList = (contrName) => {
    const name = contrName.map(item => [item.category, item.id]);
    return Array.from(
      name.map(item => {
          const dataItem = {};
          [dataItem.text, dataItem.value] = item; 
          return dataItem;
    }),);
  };

  //初始化表格格式
  initColumns = (categoryList) => {
    this.columns = [
      {
        title: '发布时间',
        dataIndex: 'publishDate',
        key: 'publishDate',
        render:(publishDate) => formateDate(publishDate)
      },
      {
        title: '发布状态',
        dataIndex: 'publishStatus',
        key: 'publishStatus',
        render:(publishStatus) => {
          if(publishStatus){
            return (<span style={{color:'red',textAlign:'center'}}>已发布</span>)
          }else{
            return (<span style={{color:'green',textAlign:'center'}}>草稿</span>)
          }
        }
      },
      {
        title: '标题',
        dataIndex: 'title',
        key: 'title',
      },
      {
        title: '类型',
        dataIndex: 'category',
        key: 'category',
        //filters: [{value: 34, text: "规章制度"},{value: 35, text: "教育培养"},{value: 36, text: "招聘招生"}],
        filters:categoryList.length >0 ? this.getCategoryList(categoryList) : null,
        filterMultiple: false,
      },
      {
        title: '操作',
        render: (noticeItem) => {
          return (
            <span className='icotr'>
              <span className='edit' onClick={() => this.editNotice(noticeItem.id)}>
                <Icon type='edit' style={{color:'#386CCA'}} />
                <span style={{color:'#386CCA'}}>编辑</span>
              </span>
              <span className='linepsan'></span>
              <span className='delete' onClick={() => this.deleteNotice(noticeItem.id)}>
                <Icon type='delete' />
                <span>删除</span>
              </span>
            </span>
          )
        }
      },
    ]
  }

  //新增新闻
  addNotice = () => {
    //不携带参数跳入新闻编辑页面
    this.props.history.push('/notice/edit');
  }

  //编辑新闻
  editNotice = async (id) => {
    //根据id获取新闻信息
    this.props.history.push('/notice/edit', id);
    // const result = await reqNoticeItem(id);
    // if(result.code === 0){
    //   //携带新闻的参数跳入新闻编辑页面
    //   const {image,context} = result.result;
    //   this.props.history.push('/notice/edit', {id});
    // }else{
    //   message.error('获取新闻失败，请稍后再试!');
    // }
  }

  //删除一条新闻
  deleteNotice = async (id) => {
    const result = await reqDeleteNotice(id);
    if(result.code === 0){
     //this.props.history.push('/notice');  //刷新页面
      //重新获取新闻列表数据
      const categoryId = this.categoryId;
      const result = await reqNoticeList({pageNum:1,pageSize:10,categoryId});
      if(result.code === 0){
        //更新state
        this.setState({
          pageNum:1,
          pageSize:10,
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        //message.error('获取新闻列表失败，请稍后再试!');
        message.error(result.msg);
      }
    }
  }

  //表格中类型筛选
  handleTableFilterChange = async (pagination, filters, sorter) => {
    //console.log(pagination,filters,sorter)
    let categoryId = filters.category[0];
    this.categoryId = categoryId;
    const result = await reqNoticeList({pageNum:1,pageSize:10,categoryId});
    if(result.code === 0){
      //更新state
      this.setState({
        pageNum:1,
        pageSize:10,
        total:result.result.total,
        dataSource:result.result.list
      })
    }else{
      //message.error('获取通知列表失败，请稍后再试!');
      message.error(result.msg);
    }
  };

  //初始化表格显示的列的格式
  // componentWillMount(){
  //   this.initColumns();
  // }

  async componentDidMount(){
    //获取通知类型下拉列表
    const list = await reqNoticeTypeList();
    if(list.code === 0){
      //携带通知的参数跳入通知编辑页面
      const categoryList = list.result;
      this.setState({categoryList})

      //初始化表格格式
      this.initColumns(categoryList);
      const result = await reqNoticeList({pageNum:1,pageSize:10});
      if(result.code === 0){
        //更新state
        this.setState({
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        //message.error('获取通知列表失败，请稍后再试!');
        message.error(result.msg);
      }
    }else{
      message.error(list.msg);
    }
  }

  render(){
    const {pageNum, pageSize, total, dataSource, loading} = this.state;
    const columns = this.columns;
    return (
      <div className="notice">
        <div className="notice-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
            <TabPane tab="通知总览" key="/notice">
            </TabPane>
          </Tabs>
        </div>
        <div className="notice-body">
          <Button type="primary" style={{width:180,height:40,margin:'0 0 20px 0'}} onClick={this.addNotice}>新增</Button>
          <LocaleProvider locale={zhCN} >
            <Table
              bordered
              rowKey='id'
              loading={loading}
              dataSource={dataSource} 
              columns={columns}
              pagination = {false}
              onChange={this.handleTableFilterChange}
            />
          </LocaleProvider>
          <div style={{marginTop:'20px',float:'right'}}>
            <LocaleProvider locale={zhCN} >
              <Pagination 
                defaultCurrent={pageNum}
                current={pageNum}
                pageSize = {pageSize}
                total = {total}
                showSizeChanger
                onShowSizeChange = {this.onShowSizeChange}
                onChange = {this.changePage}
                showTotal = {(total) => `共 ${total} 条数据`}
              />
            </LocaleProvider>
          </div>

        </div>
      </div>
    )
  }
}