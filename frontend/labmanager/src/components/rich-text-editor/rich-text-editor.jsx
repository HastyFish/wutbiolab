import React from 'react'
// 引入编辑器组件
import BraftEditor from 'braft-editor'
// 引入编辑器样式
import 'braft-editor/dist/index.css'

export default class EditorDemo extends React.PureComponent {
    constructor(props) {
        super(props);
         let text = props.context ? props.context : "";
         let context = BraftEditor.createEditorState(text);
        this.state = {
          context
        }
    }
    handleEditorChange = (context) => {
        this.setState({ context });
        //更新父组件中description状态
        //this.props.changeRichText && this.props.changeRichText(context.toHTML());
    }
    getContext = () => {
        // 返回输入数据对应的html格式的文本
        const {context} = this.state;
        const htmlContent = context.toHTML()
        return htmlContent;
      }

    // componentWillReceiveProps(nextProps){
    //   const oldcontext = nextProps.context;
    //   let context = BraftEditor.createEditorState(oldcontext);
    //   //判断是否有changeRichText且状态有变化
    //   if(this.props.changeRichText && context.toHTML() !== this.state.context.toHTML()){
    //     this.setState({
    //       context
    //     })
    //   }
    // }

    render () {
        const { context } = this.state
        return (
            <div  style={{border:'1px solid #ccc',...this.props.style}}>
                <BraftEditor
                    value={context}
                    onChange={this.handleEditorChange}
                />
            </div>
        )

    }

}