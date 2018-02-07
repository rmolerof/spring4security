class StoryBox extends React.Component {
	render (){
		
		const now = new Date();
		const topicsList = ['html', 'javascript', 'React'];
		
		return (
				<div className="is-tasty-pie">
					<h3>Stories App</h3>
					<p className="lead">
						Current time: {now.toTimeString()}
					</p>
					<h3>Topics List</h3>
					<ul>{topicsList.map((topic)=><li>{topic}</li>)}</ul>
				</div>
		);
	}
}

let target = document.getElementById('story-app');

ReactDOM.render(
		<StoryBox/>, target
);