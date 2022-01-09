import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Link } from "react-router-dom";
import TrainingPlansTable from "../trainingPlans/TrainingPlansTable";
import { Component } from "react";
import {
  deleteTrainingPlan,
  getTrainingPlans
} from "../../services/TrainingPlanService";
import keys from "../../errorKeys.json";
import { applyForTrainingPlan } from "../../services/AccountService";
import config from '../../config.json'
import { getCurrentRole } from "../../services/AuthenticationService";

class TrainingPlansComponent extends Component {

  state = {
    trainingPlans: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedTrainingPlans = {};

  async componentDidMount() {
    await this.resetTrainingPlans();
  }

  handleDelete = async trainingPlan => {
    const { t } = this.props;
    await deleteTrainingPlan(trainingPlan.number, t).then(resp => {
      if (resp && resp.status === 200) {
        const originalTrainingPlans = this.state.trainingPlans;
        const currentTrainingPlans = originalTrainingPlans.filter(
            tra => tra.number !== trainingPlan.number);
        this.setState({ trainingPlans: currentTrainingPlans });
      }
    }).catch(async ex => {
      if (ex && ex.response.data.error.errorKey
          === keys.TRAINING_PLAN_NOT_FOUND_ERROR) {
        await this.resetTrainingPlans();
      }
    });
  }

  resetTrainingPlans = async () => {
    const trainingPlans = await getTrainingPlans();
    this.setState({
      trainingPlans: trainingPlans
    });
  }

  handleApply = async trainingPlan => {
    const { t } = this.props;
    await applyForTrainingPlan(trainingPlan.number, t);
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const {
      pageSize,
      currentPage,
      sortColumn,
      trainingPlans
    } = this.state;
    const { t } = this.props;

    return (
        <div className="row mt-5">
          <div className="col">
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <TrainingPlansTable trainingPlans={trainingPlans}
                                sortColumn={sortColumn}
                                onDelete={this.handleDelete}
                                onUpdate={this.handleUpdate}
                                onApply={this.handleApply}
                                onSort={this.handleSort}
                                onRefresh={this.resetTrainingPlans}/>
            {getCurrentRole() === config.TRAINER ?
                <Link to="/trainingPlans/new"
                      className="btn btn-primary btn-lg mt-3 float-end"
                      style={{ marginBottom: 20 }}>{t('newTrainingPlan')}
                </Link> : ''}
            {/*<Pagination*/}
            {/*    itemsCount={totalCount}*/}
            {/*    pageSize={pageSize}*/}
            {/*    onPageChange={this.handlePageChange}*/}
            {/*    currentPage={currentPage}/>*/}
          </div>
        </div>
    );
  }
}

export default withTranslation()(TrainingPlansComponent);