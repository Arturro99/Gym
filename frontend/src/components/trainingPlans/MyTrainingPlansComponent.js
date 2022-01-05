import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import TrainingPlansTable from "../trainingPlans/TrainingPlansTable";
import { Component } from "react";
import { getCurrentUser } from "../../services/AuthenticationService";
import {
  getOwnTrainingPlans,
  removeTrainingPlan
} from "../../services/AccountService";
import keys from "../../errorKeys.json";

class MyTrainingPlansComponent extends Component {

  state = {
    trainingPlans: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedTrainingPlans = {};

  async componentDidMount() {
    const login = getCurrentUser();
    const trainingPlans = await getOwnTrainingPlans(login);
    this.setState({
      trainingPlans: trainingPlans
    })
  }

  handleDelete = async trainingPlan => {
    const { t } = this.props;
    const login = getCurrentUser();
    await removeTrainingPlan(trainingPlan.number, login, t).then(resp => {
      if (resp && resp.status === 200) {
        const originalTrainingPlans = this.state.trainingPlans;
        const currentTrainingPlans = originalTrainingPlans.filter(
            tra => tra.number !== trainingPlan.number);
        this.setState({ trainingPlans: currentTrainingPlans });
      }
    }).catch(async ex => {
      if (ex && ex.response.data.error.errorKey === keys.DIET_NOT_FOUND_ERROR) {
        const currentTrainingPlans = await getOwnTrainingPlans(login);
        this.setState({ trainingPlans: currentTrainingPlans });
      }
    });
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

export default withTranslation()(MyTrainingPlansComponent);