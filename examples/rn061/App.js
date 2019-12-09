import React, {PureComponent} from 'react';
import {View, Text, TouchableOpacity} from 'react-native';
import DatePicker from 'react-native-date-picker';

class App extends PureComponent {
  state = {
    date: new Date(),
  };
  render() {
    return (
      <View>
        <Text>react-native-date-picker</Text>
        <DatePicker
          date={this.state.date}
          onDateChange={date => this.setState({date})}
        />
      </View>
    );
  }
}

export default App;
