import { withTranslation } from "react-i18next";
import '../../locales/i18n';
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
    const pathParam = this.props.match.params.login;
    this.setState({
      trainingPlans: [new TrainingPlan('TRA007', 'Zdobyty', 'Strength', 5,
          'trener', 2800)]
    });
    //TODO fetch training plans
  }

  handleDelete = trainingPlan => {
    const originalTrainingPlans = this.state.trainingPlans;
    const currentTrainingPlans = originalTrainingPlans.filter(
        tra => tra.number !== trainingPlan.number);
    this.setState({ trainingPlans: currentTrainingPlans });

    //TODO implement deletion
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
      return <h1>{t('noIndividualTrainingPlans')}</h1>;
    }

    return (
        <div className="row mt-5">
          <div className="col mt-5">
            <TrainingPlansTable trainingPlans={trainingPlans}
                                myTable={true}
                                sortColumn={sortColumn}
                                onDelete={this.handleDelete}
                                onUpdate={this.handleUpdate}
                                onApply={this.handleApply}
                                onSort={this.handleSort}/>
          </div>
        </div>
    );
  }
}

export default withTranslation()(TrainingPlansComponent);