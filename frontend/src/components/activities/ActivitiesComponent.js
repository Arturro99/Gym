import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Component } from "react";
import { Link } from "react-router-dom";
import ActivitiesTable from "./ActivitiesTable";
import {
  deactivateActivity,
  getActivities
} from "../../services/ActivityService";
import keys from "../../errorKeys.json";
import {
  getCurrentRole,
  getCurrentUser
} from "../../services/AuthenticationService";
import { createBooking } from "../../services/BookingService";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";
import config from "../../config.json";

class ActivitiesComponent extends Component {

  state = {
    activities: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedActivities = {};

  async componentDidMount() {
    const { t } = this.props;
    await this.resetActivities();
  }

  handleDelete = async activity => {
    const { t } = this.props;
    await deactivateActivity(activity.number, t).then(() => {
      this.resetActivities();
    }).catch(async ex => {
      if (ex && ex.response.data.error.errorKey
          === keys.ACTIVITY_NOT_FOUND_ERROR) {
        await this.resetActivities();
      }
    });
  }

  resetActivities = async () => {
    const { t } = this.props;
    const activities = await getActivities();
    activities.forEach(activity => {
      activity.startDate = parseFromOffsetDateTimeToLegibleFormat(
          activity.startDate);
      activity.active = activity.active ? t('active') : t('inactive');
    })
    this.setState({
      activities: activities
    });
  }

  handleApply = async activity => {
    const { t } = this.props;
    await createBooking(activity.number, getCurrentUser(), t);
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const { length: count } = this.state.activities;
    const {
      pageSize,
      currentPage,
      sortColumn,
      activities
    } = this.state;
    const { t } = this.props;

    if (count === 0) {
      return <h1>{t('noActivities')}</h1>;
    }

    return (
        <div className="row mt-5">
          <div className="col">
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <ActivitiesTable activities={activities}
                             sortColumn={sortColumn}
                             onDelete={this.handleDelete}
                             onUpdate={this.handleUpdate}
                             onApply={this.handleApply}
                             onSort={this.handleSort}
                             onRefresh={this.resetActivities}/>
            {getCurrentRole() === config.TRAINER ?
                <Link to="/activities/new"
                      className="btn btn-primary btn-lg mt-3 float-end"
                      style={{ marginBottom: 20 }}>{t('newActivity')}
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

export default withTranslation()(ActivitiesComponent);