import React, { Component } from 'react';


class LoadingCanvas extends Component {

    componentDidMount() {
        this.initializeCanvas();
    }

     
    Particle(x, y, radius, color) {
    this.x = x
    this.y = y
    this.radius = radius
    this.color = color
   
    this.draw = () => {
        this.state.ctx.beginPath()
        this.state.ctx.strokeStyle = this.color;
        this.state.ctx.lineWidth = this.radius;
        // this.state.ctx.moveTo(lastPoint.x, lastPoint.y)
        this.state.ctx.moveTo(this.x, this.y);
        this.state.ctx.stroke();
        this.state.ctx.closePath()
    }
    
    this.update = function() {

      
        this.draw()
    }
}

initParticle() {
    this.setState({particle: {x: this.state.ctx.width/2,y: this.state.ctx.height/2, radius: 5,color: "blue"}})
    console.log("Width: " + this.state.canvas.width);
}

    initializeCanvas() {
        const canvas = this.refs.canvas; // get <canvas> reference to be saved in the variable, so we can use it
        this.setState({canvas: canvas})
        const ctx = this.state.canvas.getContext('2d');
        this.setState({ctx: ctx});
        this.state.ctx.fillRect(0,0, 100, 100);
        this.initParticle();
    }

    animate() {

    }

    state = { 
        particle: {
            x: 0,
            y: 0,
            radius: 0,
            color: ""
        },
        ctx: {},
        canvas: {}
     }
    render() { 
        return ( 
            <div className="row">
<div className="col">
    <canvas ref="canvas" width="150" height="150"></canvas>
</div>
            </div>
         );
    }
}
 
export default LoadingCanvas;
