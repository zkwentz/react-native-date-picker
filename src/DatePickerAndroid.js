import React from 'react'
import { StyleSheet, requireNativeComponent } from 'react-native'
import { throwIfInvalidProps } from './propChecker'
import { addMinutes, subMinutes, parseISO } from 'date-fns'

const NativeDatePicker = requireNativeComponent(
  `DatePickerManager`,
  DatePickerAndroid,
  { nativeOnly: { onChange: true } }
)

class DatePickerAndroid extends React.PureComponent {
  render() {
    if (__DEV__) throwIfInvalidProps(this.props)
    return (
      <NativeDatePicker
        {...this.props}
        date={this._date()}
        minimumDate={this._minimumDate()}
        maximumDate={this._maximumDate()}
        onChange={this._onChange}
        style={[styles.picker, this.props.style]}
        utc={this.props.timeZoneOffsetInMinutes !== undefined}
      />
    )
  }

  _onChange = e => {
    const jsDate = this._fromIsoWithTimeZoneOffset(e.nativeEvent.date)
    this.props.onDateChange && this.props.onDateChange(jsDate)
    if (this.props.onDateStringChange) {
      this.props.onDateStringChange(e.nativeEvent.dateString)
    }
  }

  _maximumDate = () =>
    this.props.maximumDate &&
    this._toIsoWithTimeZoneOffset(this.props.maximumDate)

  _minimumDate = () =>
    this.props.minimumDate &&
    this._toIsoWithTimeZoneOffset(this.props.minimumDate)

  _date = () => this._toIsoWithTimeZoneOffset(this.props.date)

  _fromIsoWithTimeZoneOffset = isoDate => {
    const date = parseISO(isoDate)
    if (this.props.timeZoneOffsetInMinutes === undefined) return date
    return subMinutes(date, this.props.timeZoneOffsetInMinutes)
  }

  _toIsoWithTimeZoneOffset = date => {
    if (this.props.timeZoneOffsetInMinutes === undefined)
      return date.toISOString()
    return addMinutes(date, this.props.timeZoneOffsetInMinutes).toISOString()
  }
}

const styles = StyleSheet.create({
  picker: {
    width: 310,
    height: 180,
  },
})

export default DatePickerAndroid
