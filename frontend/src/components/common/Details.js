import { Component } from "react";
import { Link } from "react-router-dom";

export default class Details extends Component {

  renderUpdateButton(modalId, label) {
    return (
        <button className="btn btn-primary float-end"
                data-bs-toggle='modal' data-bs-target={`#${modalId}`}>
          {label}
        </button>
    )
  }

  renderField(paramName, label, data, link = false) {
    return (
        <div className="row row-cols-2 justify-content-center">
          <label
              className="col-md-4 fw-bold text-uppercase my-2 text-start">{label}:</label>
          {link ?
              <Link className="col-md-4 my-2 text-start"
                    to={`/accounts/${data[paramName]}`}>
                {data[paramName]}
              </Link>
              :
              <label
                  className="col-md-4 my-2 text-start">{data[paramName]}</label>
          }
        </div>
    )
  }
}