import React from 'react';
import { Button, Text, View, DatePickerIOS, requireNativeComponent, StyleSheet } from 'react-native';
import moment from 'moment'

const NativeDatePicker = requireNativeComponent(`DatePickerManager`, DatePickerAndroid, { nativeOnly: { onChange: true } });

const DEFAULT_HEIGHT = 180

class DatePickerAndroid extends React.PureComponent {

    state = {
        height: 180,
    }

    static defaultProps = {
        mode: 'datetime',
        minuteInterval: 1,
    };

    render() {
        const style = [styles.picker, this.props.style, { height: this.state.height }]

        return (
            <View style={styles.container}>
                <Button title='change' onPress={() => this.setState({ height: this.state.height + 100 })} />
                <NativeDatePicker
                    {...this.props}
                    date={this._date()}
                    minimumDate={this._minimumDate()}
                    maximumDate={this._maximumDate()}
                    onChange={this._onChange}
                    style={style}
                    utc={this.props.timeZoneOffsetInMinutes !== undefined}
                />
            </View>
        )
    }

    _onChange = e => {
        const jsDate = this._fromIsoWithTimeZoneOffset(e.nativeEvent.date).toDate()
        this.props.onDateChange(jsDate)
    }

    _maximumDate = () => this.props.maximumDate && this._toIsoWithTimeZoneOffset(this.props.maximumDate);

    _minimumDate = () => this.props.minimumDate && this._toIsoWithTimeZoneOffset(this.props.minimumDate);

    _date = () => this._toIsoWithTimeZoneOffset(this.props.date);

    _fromIsoWithTimeZoneOffset = date => {
        if (this.props.timeZoneOffsetInMinutes === undefined)
            return moment(date)

        return moment.utc(date).subtract(this.props.timeZoneOffsetInMinutes, 'minutes')
    }

    _toIsoWithTimeZoneOffset = date => {
        if (this.props.timeZoneOffsetInMinutes === undefined)
            return moment(date).toISOString()

        return moment.utc(date).add(this.props.timeZoneOffsetInMinutes, 'minutes').toISOString()
    }

}

const styles = StyleSheet.create({
    picker: {
        width: 320,
        height: DEFAULT_HEIGHT,
    },
    container: {
    }
})

DatePickerAndroid.propTypes = DatePickerIOS.propTypes;

export default DatePickerAndroid;
