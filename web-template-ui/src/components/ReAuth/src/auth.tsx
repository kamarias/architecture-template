import { defineComponent, Fragment } from "vue";
import { hasAuth } from "@/router/utils";


export default defineComponent({
  name: "Auth",
  props: {
    value: {
      type: Array<string> || String,
      default: []
    }
  },
  setup(props, { slots }) {
    return () => {
      if (!slots) return null;
      return hasAuth(props.value) ? (
        <Fragment>{slots.default?.()}</Fragment>
      ) : null;
    };
  }
});
