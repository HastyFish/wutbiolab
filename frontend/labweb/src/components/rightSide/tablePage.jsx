import React from 'react';
import './index.less'
import { Breadcrumb } from 'antd';

import { getLabResearchTeam } from '@/api'

class TablePage extends React.PureComponent {
    constructor(props) {
        super(props);
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length - 1]);
        this.state = {
            titleName: "",
            dataList: {},
            navId,
            titleinfo: "",
            navName: ""
        }
    }
    // //动态获取父级传来的值再做渲染
    componentWillReceiveProps(nextProps) {
        if (nextProps.location.state) {
            this.setState({
                navId:nextProps.location.state.navId,
                titleinfo :nextProps.location.state.titleinfo,
                navName :nextProps.location.state.navName
            },()=>{ this.getTable()})
        }
    }
        //获取表格数据
        getTable = async () => {
            if(this.props.location.state){
                this.setState({
                    titleinfo :this.props.location.state.titleinfo,
                        navName :this.props.location.state.navName
                })
            }
            let data = await getLabResearchTeam(this.state.navId);
            if (data.result) {
                this.setState({
                    dataList: data.result.list,
                    navName:data.result.category
                })
            }else {
                this.setState({
                    dataList: [],
                })
            }
        }
    componentDidMount() {
        this.getTable();
    }
    jump = (data) => {
        let childList = {
            navId:data.id,
            navName:this.state.navName,
            titleinfo:this.state.titleinfo,
            infoName:data.mentorName
        }
        this.props.history.push(`/introduction/3/info`,childList);
    }

    tableTr=(data)=>{
        let num = 0;
         if(data.length){
             num = Math.ceil(data.length/8);
         }
         let newData =[...data] ;
         let arr = Array(num).fill(0);
         return arr.map((item,index)=>{
             return(
                    <tr key={index}>
                        {this.tableTd(newData.splice(0,8))}
                    </tr>
                
             )
               
         })
    }
    tableTd=(data)=>{
        let num  = 8 - data.length;
        if(num){
            for(let i=0;i<num;i++){
                data.push("")
            }
        }
        return(
            data.map((item,index)=>{
                if(item.isEmpty){
                    return(
                        <td key={index}><span style={{color: "#333"}} >{item.mentorName}</span></td>
                    )
                }else{
                    return(
                        <td key={index}><span onClick={this.jump.bind(this, item)} className="curp">{item.mentorName}</span></td>
                    )
                }
            })
        )
    }
    tableChild = (data) => data.map((item, index) => {
        return (
            <div key={index}>
                <table >
                    <thead>
                        <tr >
                            <th colSpan={8}>{item.mentorCategoryName}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.tableTr(item.labDetails)}
                    </tbody>
                    
                </table>
            </div>

        )
    })



    render() {
        let { dataList, titleinfo, navName } = this.state;
        return (
            <div className="right-container">
                <Breadcrumb separator=">">
                    <Breadcrumb.Item>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>{titleinfo}</Breadcrumb.Item>
                    <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                </Breadcrumb>
                    <div className="table-page">
                        <div className="title-name">
                            {/* {navName} */}
                        </div>
                        {
                            dataList.length > 0 && this.tableChild(dataList)
                        }
                    </div>
            </div>
        );
    }
}

export default TablePage;