import { Component } from "react";

export default class Details extends Component {

  renderField(paramName, label, data) {
    return (
        <div className="d-inline-block">
          <label className="e-start-label">{label}</label>
          <label className="e-end-label">{data[paramName]}</label>
        </div>
    )
  }
}