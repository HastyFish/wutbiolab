import React, { Component } from 'react';
import {getLabId} from '@/api'


class TableInfo extends Component {
    constructor(props) {
        super(props);
        let infoId = props.infoId;
        debugger;
        this.state = {
            infoId,
            dataList:{}
          }
    }
   componentDidMount(){
        this.getLab(this.state.infoId);
    }
        //获取表格数据
        getLab = async(id)=>{
            let data = await getLabId(id);
            if(data.result && data.result.length){
                this.setState({
                    dataList : data.result,
                    infoId:id
                })
            }
        }
    render() { 
        let {dataList} = this.state;
        return ( 
            <div>
                <div className="title-name">
                    {dataList.title}
                </div>
                <p dangerouslySetInnerHTML={{__html: dataList.context}}></p>
            </div>
         );
    }
}
 
export default TableInfo;