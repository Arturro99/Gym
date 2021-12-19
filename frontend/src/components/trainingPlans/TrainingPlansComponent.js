import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Link } from "react-router-dom";
import TrainingPlansTable from "../trainingPlans/TrainingPlansTable";
import { TrainingPlan } from "../../model/TrainingPlan";
import { Component } from "react";

class TrainingPlansComponent extends Component {

  state = {
    trainingPlans: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedTrainingPlans = {};

  componentDidMount() {
    const tra1 = new TrainingPlan(
        'TRA001',
        'Wydolnosciowy',
        'Circuit',
        3,
        'trener',
        1200
    )
    const tra2 = new TrainingPlan(
        'TRA002',
        'Silowy',
        'Strength',
        4,
        'trener',
        1500
    )
    this.setState({
      trainingPlans: [tra1, tra2]
    })
  }

  handleDelete = trainingPlan => {
    const originalTrainingPlans = this.state.trainingPlans;
    const currentTrainingPlans = originalTrainingPlans.filter(
        tra => tra.number !== trainingPlan.number);
    this.setState({ trainingPlans: currentTrainingPlans });

    //TODO implement deletion
  }

  handleUpdate = trainingPlan => {
    //TODO implement trainingPlan update
  }

  handleApply = trainingPlan => {
    //TODO implement appliance for trainingPlan
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