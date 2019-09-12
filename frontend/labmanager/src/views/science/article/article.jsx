import React,{Component} from 'react';
import {
  Tabs,
  Button,
  Table,
  Icon,
  Pagination,
  LocaleProvider
} from 'antd';

//import {reqNewsList, reqDeleteNew} from '../../api';
import {formateDate} from '@/utils/dateUtils';
import storageUtils from '@/utils/storageUtils';
import './article.less';

import zhCN from 'antd/lib/locale-provider/zh_CN';    //antd组件国际化

const {TabPane} = Tabs;

export default class News extends Component{

  state = {
    total:0,   //总数目
    pageNum: 1,  //当前页码
    pageSize:10,  //每页条数
    dataSource:[
      {
        id:'0',
        publishDate:'2018-3-4',
        publishStatus:0,
        title:'测序周报: 17条共识！美权威机构发布NGS生物信息流程标准和指南',
        category:'毕业生',
      },{
        id:'1',
        publishDate:'2018-2-14',
        publishStatus:1,
        title:'英国科学家利用SNP统计模型识别27个新抑癌基因',
        category:'毕业生',
      },{
        id:'2',
        publishDate:'2017-12-14',
        publishStatus:1,
        title:'国内首个线上赌场上线了',
        category:'毕业生',
      }
    ],  //新闻数据数组
    loading: false,  //表格数据加载时显示loading效果
  }

  //当前页码变化的回调函数
  changePage = async (page) => {
    //console.log('页码发生变化');

    //显示loading
    this.setState({
      loading:true
    })
    // const result =  await reqNewsList({pageNum:page,pageSize:this.state.pageSize});
    // //将页码重置为page，每页条数不变
    // this.setState({
    //   pageNum: page,
    //   total:result.result.total,
    //   dataSource:result.result.list
    // })
    this.setState({
      pageNum: page,
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
    //const result = await reqNews(1,pageSize);
    // const result = await reqNewsList({pageNum:1,pageSize:size});

    // //将页码重置为1，每页条数为传进来的参数
    // this.setState({
    //   pageNum: 1,
    //   pageSize:size,
    //   total:result.result.total,
    //   dataSource:result.result.list
    // })
    this.setState({
      pageNum: 1,
      pageSize:size,
    })

    //隐藏loading
    this.setState({
      loading:false
    })

  }


  initColumns = () => {
    this.columns = [
      {
        title: '发表时间',
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
            return "已发布"
          }else{
            return '草稿'
          }
        }
      },
      {
        title: '论文标题',
        dataIndex: 'title',
        key: 'title',
      },
      {
        title: '刊文名称',
        dataIndex: 'journal',
        key: 'journal',
      },
      {
        title: '第一作者',
        dataIndex: 'author',
        key: 'author',
      },
      {
        title: '发表年度',
        dataIndex: 'publication',
        key: 'publication',
      },
      {
        title: '类型',
        dataIndex: 'category',
        key: 'category',
      },
      {
        title: '操作',
        render: (newItem) => {
          return (
            <span className='icotr'>
              <span className='edit' onClick={() => this.editNew(newItem.id)}>
                <Icon type='edit' style={{color:'#386CCA'}} />
                <span style={{color:'#386CCA'}}>编辑</span>
              </span>
              <span className='linepsan'></span>
              <span className='delete' onClick={() => this.deleteNew(newItem.id)}>
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
  addNews = () => {
    //不携带参数跳入新闻编辑页面
    this.props.history.push('/science/edit');
  }

  //编辑新闻
  editNew = async (id) => {
    //根据id获取新闻信息
    this.props.history.push('/science/edit', id);
    // const result = await reqNewItem(id);
    // if(result.code === 0){
    //   //携带新闻的参数跳入新闻编辑页面
    //   const {imageList,context} = result.result;
    //   this.props.history.push('/news/edit', {id});
    // }else{
    //   message.error('获取新闻失败，请稍后再试!');
    // }
  }

  //删除一条新闻
  deleteNew = async (id) => {
    //const result = await reqDeleteNew(id);
    //if(result.code === 0){
     // //this.props.history.push('/news');  //刷新页面
      //重新获取新闻列表数据
      // const result = await reqNewsList({pageNum:1,pageSize:10});
      // if(result.code === 0){
      //   //更新state
      //   this.setState({
      //     total:result.total,
      //     dataSource:result.result.list
      //   })
      // }else{
      //   message.error('获取新闻列表失败，请稍后再试!');
      // }
    //}
  }

  //初始化表格显示的列的格式
  componentWillMount(){
    const user = storageUtils.getUser() || {};
    if(!user || !user.username){
      //自动跳转到登陆
      this.props.history.replace('/login')
    }else{
      this.initColumns();
    }
  }

  async componentDidMount(){
    //初始化
    // const result = await reqNewsList({pageNum:1,pageSize:10});
    // if(result.code === 0){
    //   //更新state
    //   this.setState({
    //     total:result.result.total,
    //     dataSource:result.result.list
    //   })
    // }else{
    //   message.error('获取新闻列表失败，请稍后再试!');
    // }
  }

  render(){
    const {pageNum, pageSize, total, dataSource, loading} = this.state;
    const columns = this.columns;
    return (
      <div className="article">
        <div className="article-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
            <TabPane tab="论文总览" key="/science">
            </TabPane>
            <TabPane tab="学术总览" key="/science/academic">
            </TabPane>
          </Tabs>
        </div>
        <div className="article-body">
          <Button type="primary" style={{width:180,height:40,margin:'0 0 20px 0'}} onClick={this.addNews}>新增</Button>
          <Table
            bordered
            rowKey='id'
            loading={loading}
            dataSource={dataSource} 
            columns={columns}
            pagination = {false}
          />
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