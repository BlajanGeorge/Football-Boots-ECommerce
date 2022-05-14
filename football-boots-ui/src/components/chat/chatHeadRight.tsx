import { Box, Chip, Typography } from "@mui/material";
import classes from "./chatHead.module.css"

export const ChatHeadRight = (props: any) => {
    return (
        <Box className={classes.chat_container} >
            <Typography className={classes.text}>{props.email}</Typography>
            <Chip label={props.text} className={classes.chip_right} />
        </Box>
    )
}; 