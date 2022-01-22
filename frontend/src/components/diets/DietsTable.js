import React, { Component } from "react";
import Table from "../common/Table";
import { withTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from '../../config.json'
import ConfirmModal from "../common/ConfirmModal";

class DietsTable extends Component {

  state = {
    deleteModalId: 'confirmDietDeletion',
    diet: ''
  }

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
                    data-bs-toggle='modal'
                    data-bs-target={`#${this.state.deleteModalId}`}
                    onClick={() => this.setState({ diet: diet })}>
                  {this.props.t('delete')}
                </button> : ''
            }
            {this.props.myTable ?
                <button
                    className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                    data-bs-toggle='modal'
                    data-bs-target={`#${this.state.deleteModalId}`}
                    onClick={() => this.setState({ diet: diet })}>
                  {this.props.t('cancel')}
                </button> :
                <button
                    className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                    onClick={() => this.props.onApply(diet)}>
                  {this.props.t('apply')}
                </button>
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/diets/${diet.number}`}>
              {this.props.t('details')}
            </Link>
          </div>
    }
  ];

  render() {
    const {
      diets,
      onSort,
      sortColumn,
      t,
      onDelete,
      myTable,
      onRefresh
    } = this.props;
    const { diet, deleteModalId } = this.state;

    return (
        <div className="card-header bg-light">
          <ConfirmModal title={myTable ? 'confirm_diet_cancellation_title'
              : 'confirm_diet_cancellation_title'}
                        message={myTable ? 'confirm_diet_cancellation_message'
                            : 'confirm_diet_cancellation_message'}
                        modalId={`${deleteModalId}`}
                        onConfirm={onDelete}
                        object={diet}
                        objectBusinessId={diet.number}/>
          {this.props.myTable ?
              <h1 className="text-center">{t('myDiets')}</h1>
              : <h1 className="text-center">{t('diets')}</h1>}
          <Table columns={this.columns}
                 data={diets}
                 sortColumn={sortColumn}
                 onSort={onSort}
                 onRefresh={onRefresh}
                 t={t}/>
        </div>
    )
  }
}

export default withTranslation()(DietsTable);