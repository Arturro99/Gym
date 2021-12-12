import { Component } from "react";
import Table from "../common/Table";
import { withTranslation } from "react-i18next";

class DietsTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'name', label: 'name'
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
          <div>
            <button className="btn btn-outline-danger"
                    onClick={() => this.props.onDelete(diet)}>{this.props.t(
                'delete')}
            </button>
            {this.props.myTable ? null :
                <button className="btn btn-outline-info ms-2"
                        onClick={() => this.props.onUpdate(diet)}>{this.props.t(
                    'update')}
                </button>
            }
            {this.props.myTable ? null :
                <button className="btn btn-outline-success ms-2"
                        onClick={() => this.props.onApply(diet)}>{this.props.t(
                    'apply')}
                </button>
            }
          </div>
    }
  ];

  render() {
    const { diets, onSort, sortColumn, t } = this.props;
    return (
        <div
            className="position-absolute top-25 start-50 translate-middle mt-5">
          {this.props.myTable ?
              <h1 className="text-center">{t('myDiets')}</h1>
              : null}
          <Table columns={this.columns}
                 data={diets}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(DietsTable);