import React, { useState } from 'react'
import { Button, TextInput, Text } from 'react-native'

export default function Scroll(props) {
    const [wheelIndex, setWheelIndex] = useState("0")
    const [scrollTimes, setScrollTimes] = useState("1")

    return (
        <>
            <Text>Wheel index</Text>
            <TextInput
                testID="wheelIndex"
                style={style}
                onChangeText={setWheelIndex}
                value={wheelIndex}
            />
            <Text>Scroll times</Text>
            <TextInput
                testID="scrollTimes"
                style={style}
                onChangeText={setScrollTimes}
                value={scrollTimes}
            />
            <Button
                testID={"doScroll"}
                title={`Scroll`}
                onPress={() => props.scroll({ wheelIndex: Number.parseInt(wheelIndex), scrollTimes: Number.parseInt(scrollTimes) })}
            />
            <Button
                testID={"reset"}
                title={`Reset`}
                onPress={props.reset}
            />
        </>
    )
}

const style = { height: 40, borderColor: 'gray', borderWidth: 1 }