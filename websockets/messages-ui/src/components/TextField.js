import React from 'react'
import {makeStyles, withStyles,} from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';

const CssTextField = withStyles({
    root: {
        [`& fieldset`]: {
            borderRadius: 30,
        },
        '& label.Mui-focused': {
            color: 'black',
            opacity: .6
        },
        '& .MuiInput-underline:after': {
            borderBottomColor: 'black',
        },
        '& .MuiOutlinedInput-root': {
            '& fieldset': {
                borderColor: '#212121',
                opacity: .4
            },
            '&:hover fieldset': {
                borderColor: 'black',
            },
            '&.Mui-focused fieldset': {
                borderColor: 'black',
            },
        },
    },
})(TextField);

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
            width: '50%',
        },
    }
}));

export default function MessageInput({onInputChange, textInput}) {
    const classes = useStyles();

    return (
        <div className={classes.root} noValidate>
            <CssTextField
                value={textInput}
                onChange={onInputChange}
                label="Message"
                variant="outlined"
                id="custom-css-outlined-input"
            />
        </div>
    );
}
