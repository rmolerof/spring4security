class StoryBox extends React.Component {
	render (){
		return (<div>Hello from React: StoryBox</div>);
	}
}

let target = document.getElementById('story-app');

ReactDOM.render(
		<StoryBox/>, target
);