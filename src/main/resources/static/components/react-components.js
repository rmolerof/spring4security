class Comment extends React.Component {
	render (){
		return (
				<div className="comment">
					<div className="note note-info">
						<img className="avatarUrl" src={this.props.avatarUrl} alt={`${this.props.author}'s movie`}/>
						<p className="comment-header">{this.props.author}</p>
						<p className="comment-body">
							{this.props.body}
						</p>
						<div className="comment-actions">
							<a href="#" className="comment-footer-delete">
								Delete comment
							</a>
						</div>
					</div>
				</div>
		);
	}
}

class CommentBox extends React.Component{
	
	_getComments(){
		const commentList = [
			{id: 1, author: 'Morgan Freeman', body: 'Great picture', avatarUrl: 'assets/layouts/layout3/img/avatar3.jpg'},
			{id: 2, author: 'Bending Bender', body: 'Excellent stuff', avatarUrl: 'assets/layouts/layout3/img/avatar2.jpg'}
		];
		return commentList.map((comment)=>{
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
	
	render(){
		const comments = this._getComments();
		return(
				<div className="comment-box">
					<h3>Comments</h3>
					<h4 className="comment-count">{this._getCommentsTitle(comments.length)}</h4>
					<div className="comment-list">
						{comments}
					</div>
				</div>
				);
	}
}

let target = document.getElementById('story-app');

ReactDOM.render(
		<CommentBox/>, target
);
