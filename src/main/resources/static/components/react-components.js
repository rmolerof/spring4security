class Comment extends React.Component {
	
	constructor(){
		super();
		this.state = {
			isAbusive: false	
		};
	}
	
	render (){
		let commentBody;
		let abuseText = "Report as Abuse";
		if(!this.state.isAbusive){
		    commentBody = this.props.body;
	    } else {
	        commentBody = <em>Content marked as abusive</em>
	        abuseText = "Re-enable Comment";
	    }
		return (
				<div className="comment">
					<div className="note note-info">
						<img className="avatarUrl" src={this.props.avatarUrl} alt={`${this.props.author}'s movie`}/>
						<p className="comment-header">{this.props.author}</p>
						<p className="comment-body">
							{commentBody}
						</p>
						<div className="comment-actions">
							<a href="#" className="comment-footer-delete">
								Delete comment
							</a>&#160;&#160; 
							<a href="#" onClick={this._toggleAbuse.bind(this)}>{abuseText}</a>
						</div>
					</div>
				</div>
		);
	}
	
	_toggleAbuse(event){
		event.preventDefault();
		this.setState({isAbusive: !this.state.isAbusive});
	}
}

class CommentBox extends React.Component{
	
	constructor(){
		super();
		this.state = {
			showComments: false,
			comments: []
		};
	}
	
	_getComments(){
		return this.state.comments.map((comment)=>{
			return (<Comment author={comment.author} body={comment.body} avatarUrl={comment.avatarUrl} key={comment.id}/>);
		});
	}
	
	_getCommentsTitle(commentCount){
		if(commentCount === 0){
			return 'No comments yet';
		} else if (commentCount === 1){
			return '1 comment'
		} else {
			return `${commentCount} comments`;
		}
	}
	
	_handleClick(){
		this.setState({showComments: !this.state.showComments});
	}
	
	_addComment(author, body){
		const comment= {
			id: this.state.comments.length + 1,
			author,
			body,
			avatarUrl: 'assets/layouts/layout3/img/avatar1.jpg'
		};
		this.setState({comments: this.state.comments.concat([comment])});
	}
	
	_fetchComments(){
		jQuery.ajax({
			method: 'GET',
			url: 'https://jsonplaceholder.typicode.com/posts',
			success: (comments) => {
				var commentsResponse = [];
				for(var i = 0; i< 5; i++){
					var comment = {
							id: comments[i].id, 
							author: comments[i].userId, 
							body: comments[i].body, 
							avatarUrl: 'assets/layouts/layout3/img/avatar3.jpg'};
					commentsResponse.push(comment);
				}
				this.setState({comments: commentsResponse})
			}
		});
	}
	
	_getAvatars(){
		return this.state.comments.map((comment) => {
			return comment.avatarUrl;
		});
	}
	
	componentWillMount(){
		this._fetchComments();
	}
	
	render(){
		const comments = this._getComments();
		const avatars = this._getAvatars();
		let commentNodes;
		let buttonText = 'Show comments';
		if(this.state.showComments){
			commentNodes = <div className="comment-list">{comments}</div>;
			buttonText = 'Hide comments';
		}
		return(
				<div className="comment-box">
					<CommentForm addComment={this._addComment.bind(this)}/>
					<CommentAvatarList avatars={this._getAvatars()}/>
					<h3>Comments</h3>
					<h4 className="comment-count">{this._getCommentsTitle(comments.length)}</h4>
					<button onClick={this._handleClick.bind(this)}>{buttonText}</button>
					{commentNodes}
				</div>			
		);
	}
	
	componentDidMount(){
		this._timer = setInterval(() => this._fetchComments(), 5000);
	}
	
	componentWillUnmount(){
		clearInterval(this._timer);
	}
}

class CommentForm extends React.Component {
	
	constructor(){
		super();
		this.state = {
			characters: 0	
		};
	}
	
	render(){
		return(
			<form className="comment-form" onSubmit={this._handleSubmit.bind(this)}>
			<label>Join the discussion</label>
			<div className="comment-form-fields">
				<input placeholder="Name: " ref={(input) => this._author = input}/><br />
				<br />
				<textarea 
					placeholder="Comment: " 
					ref={(textarea) => this._body = textarea}
					onKeyUp={this._getCharacterCount.bind(this)}
				></textarea>
			</div>
			<p>{this.state.characters} characters</p>
			<div className="comment-form-actions">
				<button type="submit">
					Post comment
					</button>
				</div>
			</form>
		);
	}
	
	_handleSubmit(event){
		event.preventDefault(); //Page doesn't reload when form is submitted
		
		let author = this._author;
		let body = this._body;
		
		if (!this._author.value || !this._body.value) {
			alert('Please enter your name and comment');
			return;
		}
		
		this.props.addComment(author.value, body.value);
	}
	
	_getCharacterCount() {
		this.setState({characters: (this._body.value).length});
	}
}

class CommentAvatarList extends React.Component {
	render() {
		const {avatars = []} = this.props;
		return (
			<div className="comment-avatars">
				<h4>Authors</h4>
				<ul>
					{avatars.map((avatarUrl, i) => (
						<li key={i}>
							<img src={avatarUrl}/>
						</li>
					))}
				</ul>
			</div>
		);
	}
}

let target = document.getElementById('story-app');

ReactDOM.render(
		<CommentBox/>, target
);
