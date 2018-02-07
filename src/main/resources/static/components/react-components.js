class Comment extends React.Component {
	render (){
		
		
		return (
				<div className="comment">
					<div className="note note-info">
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
	render(){
		return(
				<div className="comment-box">
					<h3>Comments</h3>
					<h4 className="comment-count">2 comments</h4>
					<div className="comment-list">
						<Comment author="Morgan Freeman" body="great picture"/>
						<Comment author="Bending Bender" body="Excellent statue"/>
					</div>
				</div>
				);
	}
}

let target = document.getElementById('story-app');

ReactDOM.render(
		<CommentBox/>, target
);