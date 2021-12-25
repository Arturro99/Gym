import { Component } from "react";
import Table from "../common/Table";
import { withTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from '../../config.json'

class DietsTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'title', label: 'name'
    },
    {
      path: 'dietType', label: 'dietType'
    },
    {
      path: 'calories', label: 'calories'
    },
    {
      path: 'mealsNumber', label: 'mealsNumber'
    },
    {
      path: 'price', label: 'price'
    },
    {
      key: 'utils', label: 'actions',
      content: diet =>
          <div className="row justify-content-md-center">
            {!this.props.myTable && getCurrentRole() !== config.CLIENT ?
                <button
                    className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                    onClick={() => this.props.onDelete(diet)}>
                  {this.props.t('delete')}
                </button> : ''
            }
            {this.props.myTable ?
                <button
                    className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                    onClick={() => this.props.onDelete(diet)}>
                  {this.props.t('cancel')}
                </button> :
                <button
                    className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                    onClick={() => this.props.onApply(diet)}>{this.props.t(
                    'apply')}
                </button>
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/diets/${diet.number}`}>{this.props.t(
                'details')}
            </Link>
          </div>
    }
  ];

  render() {
    const { diets, onSort, sortColumn, t } = this.props;
    return (
        <div className="card-header">
          {this.props.myTable ?
              <h1 className="text-center">{t('myDiets')}</h1>
              : <h1 className="text-center">{t('diets')}</h1>}
          <Table columns={this.columns}
                 data={diets}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(DietsTable);