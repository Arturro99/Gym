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
import { getCurrentUser } from "../../services/AuthenticationService";

class TrainingPlansComponent extends Component {

  state = {
    trainingPlans: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedTrainingPlans = {};

  async componentDidMount() {
    const trainingPlans = await getTrainingPlans();
    this.setState({
      trainingPlans: trainingPlans
    })
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
        const currentTrainingPlans = await getTrainingPlans();
        this.setState({ trainingPlans: currentTrainingPlans });
      }
    });
  }

  handleApply = async trainingPlan => {
    const { t } = this.props;
    await applyForTrainingPlan(trainingPlan.number, getCurrentUser(), t);
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const { length: count } = this.state.trainingPlans;
    const {
      pageSize,
      currentPage,
      sortColumn,
      trainingPlans
    } = this.state;
    const { t } = this.props;

    if (count === 0) {
      return <h1>{t('noTrainingPlans')}</h1>;
    }

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
                                onSort={this.handleSort}/>
            <Link to="/trainingPlans/new"
                  className="btn btn-primary btn-lg mt-3 float-end"
                  style={{ marginBottom: 20 }}>{t('newTrainingPlan')}
            </Link>
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